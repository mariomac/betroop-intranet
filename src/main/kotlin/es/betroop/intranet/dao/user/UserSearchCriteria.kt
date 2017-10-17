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

package es.betroop.intranet.dao.meeting

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import es.betroop.intranet.dao.SearchCriteria
import es.betroop.intranet.model.UserRole
import java.util.*

/**
 * Created by mmacias on 27/9/16.
 */
class UserSearchCriteria(val page: Int = 1, val id: Long? = null, val text: String? = null, val rol: Int? = null,
                         val orderBy: String? = null, val orderByAsc: Boolean = true, val nullEmail: Boolean = false
) : SearchCriteria {
    companion object {
        val reader = jacksonObjectMapper().reader().forType(UserSearchCriteria::class.java)
        fun fromJson(json: String): UserSearchCriteria {

            val tree = reader.readValue<UserSearchCriteria>(json)
            return tree
        }
    }

    override fun build(columnPrefix:String): Pair<String, ArrayList<Any>> {
        val values = ArrayList<Any>()
        val criteria = ArrayList<String>()
        if (id != null) {
            criteria += "id = ?"
            values += id
        }
        if (text != null && text.trim() != "") {
            criteria += "(name like ? OR description like ?)"
            values += "%$text%"
            values += "%$text%"
        }
        if (UserRole.fromInt(rol) != UserRole.ALL) {
            criteria += "rol = ?"
            values += rol!!
        }
        if(nullEmail) {
            criteria += "email IS null"
        }
        if (criteria.size > 0) {
            return Pair(" WHERE ${criteria.joinToString(" AND ")}", values)
        } else {
            return Pair("", values)
        }
    }
}