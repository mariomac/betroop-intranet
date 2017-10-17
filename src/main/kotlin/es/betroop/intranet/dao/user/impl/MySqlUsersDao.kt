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

package es.betroop.intranet.dao.user.impl

import es.betroop.intranet.dao.meeting.UserSearchCriteria
import es.betroop.intranet.dao.user.DbUser
import es.betroop.intranet.dao.user.UsersDao
import es.betroop.intranet.dao.user.toDb
import es.betroop.intranet.dao.user.toModel
import es.betroop.intranet.model.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.Timestamp

/**
 * Created by mmacias on 28/9/16.
 */
@Component
open class MySqlUsersDao : UsersDao {

    val log = LoggerFactory.getLogger(MySqlUsersDao::class.java)

    @Value("\${db.page.entries}")
    var dbPageEntries: Int = 10

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    override fun find(sc: UserSearchCriteria): List<User> {
        var page = sc.page
        val from = (page - 1) * dbPageEntries

        var statement = StringBuilder("SELECT id, name, email, create_time, photoUrl, image_modified, description, rol" +
                " FROM user ")

        val criteriaArgsPair = sc.build()

        var args = criteriaArgsPair.second
        statement.append(criteriaArgsPair.first)

        if (sc.orderBy != null) {
            statement.append(" ORDER BY ${sc.orderBy} ${if (sc.orderByAsc) "ASC" else "DESC"}")
        }
        statement.append(" LIMIT ?, ?")

        args.add(from)
        args.add(dbPageEntries)

        log.debug("from = $from + pageEntries = $dbPageEntries")

        val sqlStatement = statement.toString()

        log.debug("SQL statement: ${sqlStatement}")

        return jdbcTemplate.query(sqlStatement, args.toArray(), UsersRowMapper())
                .map { it.toModel() }
    }

    override fun count(sc: UserSearchCriteria): Long {
        val criteriaArgsPair = sc.build()
        val sql = "SELECT COUNT(*) FROM user ${criteriaArgsPair.first}"
        log.debug("count SQL: $sql")
        return jdbcTemplate.queryForObject(sql,
                criteriaArgsPair.second.toArray(),
                Long::class.java)
    }

    override fun update(value: User) {
        val u = value.toDb()
        val sql = """UPDATE user SET name=?, create_time=?,
                    photoUrl=?, image_modified=?, description=?,
                    rol=? WHERE id=?"""
        log.debug("update SQL: $sql")
        jdbcTemplate.update(
                sql,
                u.name, u.createTime, u.photoUrl, u.imageModified,
                u.description, u.role, u.id
        )
    }

    override fun insert(user: User): Long {
        val u = user.toDb()
        val sql = """INSERT INTO user (name, create_time,
                    photoUrl, image_modified, description, rol)
                    VALUES (?,?,?,?,?,?)"""
        log.debug("insert SQL: $sql")
        jdbcTemplate.update(
                sql,
                u.name, Timestamp(System.currentTimeMillis()),
                u.photoUrl, u.imageModified, u.description, u.role
        )
        return -1 // the id is not important at the moment
    }
}

internal class UsersRowMapper : RowMapper<DbUser> {
    override fun mapRow(rs: ResultSet?, rowNum: Int): DbUser =
            DbUser(rs!!.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getTimestamp("create_time"),
                    rs.getString("photoUrl"),
                    rs.getInt("image_modified"),
                    rs.getString("description"),
                    rs.getInt("rol"))

}