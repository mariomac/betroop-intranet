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

package es.betroop.intranet.dao.webadmin.impl

import es.betroop.intranet.dao.EventSearchCriteria
import es.betroop.intranet.dao.event.EventsDao
import es.betroop.intranet.dao.toDb
import es.betroop.intranet.dao.toModel
import es.betroop.intranet.model.DbEvent
import es.betroop.intranet.model.Event
import es.betroop.intranet.model.EventType
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class MySqlEventsDao : EventsDao {

    @Value("\${db.page.entries}")
    var dbPageEntries: Int = 10

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    internal var log = LoggerFactory.getLogger(MySqlEventsDao::class.java)

    override fun find(sc: EventSearchCriteria): List<Event> {
        var page = sc.page
        val from = (page - 1) * dbPageEntries
        var statement = StringBuilder("SELECT id, name, highlighting, photoUrl, tag, type, event_time, event_type FROM event")

        val criteriaArgsPair = sc.build()
        var args = criteriaArgsPair.second
        statement.append(criteriaArgsPair.first)

        statement.append(" ORDER BY event_time ASC LIMIT ?, ?")
        args.add(from)
        args.add(dbPageEntries)

        val sqlStatement = statement.toString()

        log.debug("SQL statement: ${sqlStatement}")

        return jdbcTemplate.query(sqlStatement, args.toArray(), EventsRowMapper())
                .map {  it.toModel() }
    }

    override fun count(searchCriteria: EventSearchCriteria): Long {
        val criteriaArgsPair = searchCriteria.build()
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM event ${criteriaArgsPair.first}",
                criteriaArgsPair.second.toArray(),
                Long::class.java)
    }



    override fun insert(event: Event): Long {
        val dbe = event.toDb()
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({ connection: Connection ->
            val ps = connection.prepareStatement("INSERT INTO event(name, highlighting, photoUrl, tag, type, event_time, event_type) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)
            ps.setString(1, dbe.name)
            ps.setInt(2, dbe.highlighting)
            ps.setString(3, dbe.photoUrl)
            ps.setString(4, dbe.tag)
            ps.setString(5, dbe.type)
            ps.setTimestamp(6, dbe.event_time)
            ps.setInt(7, dbe.event_type ?: EventType.OTROS.toInt)

            ps
        }, keyHolder)

        return keyHolder.key.toLong()
    }


    override fun update(event: Event) {
        val dbe = event.toDb()
        jdbcTemplate.update({ connection: Connection ->
            val ps = connection.prepareStatement("UPDATE event SET name=?, highlighting=?, photoUrl=?, tag=?, type=?, event_time=?, event_type=? WHERE id=?")

            ps.setString(1, dbe.name)
            ps.setInt(2, dbe.highlighting)
            ps.setString(3, dbe.photoUrl)
            ps.setString(4, dbe.tag)
            ps.setString(5, dbe.type)
            ps.setTimestamp(6, dbe.event_time)
            ps.setInt(7, dbe.event_type ?: EventType.OTROS.toInt)
            ps.setLong(8, dbe.id!!)
            ps
        })
    }
}

internal class EventsRowMapper : RowMapper<DbEvent> {
    override fun mapRow(rs: ResultSet?, rowNum: Int): DbEvent =
            DbEvent(rs!!.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("highlighting"),
                    rs.getString("photoUrl"),
                    rs.getString("tag"),
                    rs.getString("type"),
                    rs.getTimestamp("event_time"),
                    rs.getInt("event_type")
            )
}