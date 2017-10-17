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

import es.betroop.intranet.dao.webadmin.WebAdminDao
import es.betroop.intranet.utils.HashTool
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
open class MySqlWebAdminDao(): WebAdminDao {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    internal var log = LoggerFactory.getLogger(MySqlWebAdminDao::class.java)

    override fun checkPassword(email: String, introducedPassword: String): Boolean {
        log.trace("checkPassword")
        var password : String? = jdbcTemplate.queryForObject("SELECT password FROM webadmin WHERE email = ?",arrayOf(email),String::class.java)
        return password != null && password.equals(HashTool.hash(introducedPassword),ignoreCase = true)
    }
}
