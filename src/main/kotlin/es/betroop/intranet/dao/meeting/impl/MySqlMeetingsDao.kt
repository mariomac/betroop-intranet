/*------------------------------------------------------------------------------
 * This code is distributed under a BEER-WARE license.
 * -----------------------------------------------------------------------------
 * Mario Macias Lloret wrote this file. Considering this, you can do what you
 * want with it: modify it, redistribute it, sell it, etc... But you will always
 * have to credit me as an author in the code.
 *
 * In addition, if we meet someday and you think this code has been useful for
 * you, you MUST pay me a beer (a good one, if possible) as a reward for my
 * contribution.
 * -----------------------------------------------------------------------------
 */

package es.betroop.intranet.dao.meeting.impl

import es.betroop.intranet.dao.Dao
import es.betroop.intranet.dao.meeting.*
import es.betroop.intranet.model.Meeting
import es.betroop.intranet.model.aux.MeetingLocation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.Statement
import java.sql.Timestamp

/**
 * Created by mmacias on 28/9/16.
 */
@Component
class MySqlMeetingsDao : MeetingsDao {
    @Value("\${db.page.entries}")
    var dbPageEntries: Int = 10

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    internal var log = LoggerFactory.getLogger(MySqlMeetingsDao::class.java)

    override fun find(sc: MeetingSearchCriteria): List<Meeting> {
        var page = sc.page
        val from = (page - 1) * dbPageEntries
        var criteriaArgsPair = sc.build(columnPrefix="m.")

        var statement = """
                SELECT m.id, m.name, m.event_id, m.num_asistentes, m.type, m.smoke, m.alcohol,
                       m.team, m.description, m.start_time, m.finish_time, m.tag, m.latitude,
                        m.longitude, m.active, uhm.user_id, u.name, e.name
                FROM meeting as m, user_has_meeting as uhm, user as u, event as e
                WHERE uhm.user_id = u.id AND uhm.meeting_id=m.id AND uhm.status=${Meeting.statusOwner}
                AND e.id = m.event_id ${criteriaArgsPair.first}
                ORDER BY id DESC LIMIT ?, ?
        """

        var args = criteriaArgsPair.second

        args.add(from)
        args.add(dbPageEntries)

        val sqlStatement = statement.toString()

        log.debug("SQL statement: ${sqlStatement}")

        return jdbcTemplate.query(sqlStatement, args.toArray(), MeetingRowMapper())
                .map {
                    val model = it.toModel()
                    model.extra!!.location = getAddress(it.id!!)
                    model
                }
    }

    override fun count(sc: MeetingSearchCriteria): Long {
        val criteriaArgsPair = sc.build()
        val sql = "SELECT COUNT(*) FROM meeting ${criteriaArgsPair.first}"
        log.debug("count SQL: $sql")
        return jdbcTemplate.queryForObject(sql,
                criteriaArgsPair.second.toArray(),
                Long::class.java)
    }

    override fun insertLocation(location: MeetingLocation) {
        updateLatLong(location)
        val sql = "INSERT INTO address(street,city,postal_code,country,meeting_id) VALUES (?,?,?,?,?)"
        jdbcTemplate.update(sql, location.street, location.city, location.postalCode, location.country, location.meetingId)
    }

    override fun updateLocation(location: MeetingLocation) {
        updateLatLong(location)
        val sql = "UPDATE address SET street=?, city=?, postal_code=?, country=? WHERE meeting_id=?"
        jdbcTemplate.update(sql, location.street, location.city, location.postalCode, location.country, location.meetingId)
    }

    private fun updateLatLong(meeting: MeetingLocation) {
        val sql = "UPDATE meeting " +
                "SET latitude = ?, longitude = ? " +
                "WHERE id=?";
        jdbcTemplate.update(sql, meeting.latitude, meeting.longitude, meeting.meetingId)
    }

    override fun getLastLocationForOwner(ownerId: Long): MeetingLocation? {
        val sql = """select m.id, m.latitude, m.longitude
                     from meeting as m, user_has_meeting as uhm
                     where m.id = uhm.meeting_id and uhm.user_id=? and uhm.status=?
                     order by id desc limit 1"""
        var meetingId: Long? = null
        var latitude: Double = 0.0
        var longitude: Double = 0.0
        jdbcTemplate.query(
                sql,
                { resultSet ->
                    meetingId = resultSet.getLong(1)
                    latitude = resultSet.getDouble(2)
                    longitude = resultSet.getDouble(3)
                },

                arrayOf(ownerId,
                        Meeting.statusOwner)
        )
        return meetingId?.let { mid ->
            val asql = """select street, city, postal_code, country
                          from address
                          where meeting_id = ? limit 1"""
            try {
                jdbcTemplate.queryForObject(asql,
                        RowMapper { resultSet, i ->
                            MeetingLocation(
                                    meetingId = mid,
                                    latitude = latitude,
                                    longitude = longitude,
                                    street = resultSet.getString("street") ?: "",
                                    city = resultSet.getString("city") ?: "",
                                    postalCode = resultSet.getString("postal_code") ?: "",
                                    country = resultSet.getString("country") ?: ""
                            )
                        },
                        mid)
            } catch(e:EmptyResultDataAccessException) {
                return null
            }
        }
    }


    override fun assignOwnerToMeeting(meetingId: Long, ownerId: Long) {
        val sql = "INSERT INTO user_has_meeting(user_id,meeting_id,status) VALUES (?,?,?)"
        jdbcTemplate.update(sql, ownerId, meetingId, Meeting.statusOwner)
    }

    override fun update(meeting: Meeting) {
        val dbm = meeting.toDb()
        var sql = """
            UPDATE meeting
            SET name = ?, description = ?, active = ?, alcohol = ?, smoke = ?,
            num_asistentes = ?, team = ?, tag = ?
            WHERE id = ?
        """
        jdbcTemplate.update(sql, dbm.name, dbm.description, dbm.active, dbm.alcohol,
                dbm.smoke, dbm.numAsistentes, dbm.team, dbm.tag, dbm.id)
    }

    override fun insert(meeting: Meeting): Long {
        val dbo = meeting.toDb()
        val sql = """
                INSERT INTO meeting(name,event_id,num_asistentes,type,smoke,alcohol,team,
                    description,create_time,start_time,finish_time,tag,active)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)
                """
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
                PreparedStatementCreator { connection ->
                    val stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                    stmt.setString(1, dbo.name)
                    stmt.setLong(2, dbo.eventId)
                    stmt.setInt(3, dbo.numAsistentes)
                    stmt.setInt(4, dbo.type)
                    stmt.setInt(5, dbo.smoke)
                    stmt.setInt(6, dbo.alcohol)
                    stmt.setString(7, dbo.team)
                    stmt.setString(8, dbo.description)
                    stmt.setTimestamp(9, Timestamp(System.currentTimeMillis()))
                    stmt.setTimestamp(10, dbo.startTime)
                    stmt.setTimestamp(11, dbo.finishTime)
                    stmt.setString(12, dbo.tag)
                    stmt.setInt(13, dbo.active)

                    stmt

                },
                keyHolder)

        return keyHolder.key.toLong()
    }

    override fun getAddress(meetingId: Long): MeetingLocation? {
        val sql = """SELECT street, city, postal_code, country
                     FROM address
                     WHERE meeting_id=?"""
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    RowMapper { resultSet, i ->
                        MeetingLocation(
                                street = resultSet.getString("street"),
                                city = resultSet.getString("city"),
                                postalCode = resultSet.getString("postal_code"),
                                country = resultSet.getString("country"),
                                meetingId = meetingId
                        )
                    },
                    meetingId)
        } catch(e:EmptyResultDataAccessException) {
            return null
        }
    }
}

internal class MeetingRowMapper : RowMapper<DbMeeting> {
    override fun mapRow(rs: ResultSet?, rowNum: Int): DbMeeting =
            DbMeeting(
                    id = rs!!.getLong("id"),
                    name = rs.getString("name"),
                    eventId = rs.getLong("event_id"),
                    numAsistentes = rs.getInt("num_asistentes"),
                    type = rs.getInt("type"),
                    smoke = rs.getInt("smoke"),
                    alcohol = rs.getInt("alcohol"),
                    team = rs.getString("team"),
                    description = rs.getString("description"),
                    startTime = rs.getTimestamp("start_time"),
                    finishTime = rs.getTimestamp("finish_time"),
                    tag = rs.getString("tag"),
                    latitude = rs.getDouble("latitude"),
                    longitude = rs.getDouble("longitude"),
                    active = rs.getInt("active"),
                    extra = DbMeeting.Extra(
                            ownerId = rs.getLong("uhm.user_id"),
                            ownerName = rs.getString("u.name"),
                            eventName = rs.getString("e.name")
                    )
            )

}