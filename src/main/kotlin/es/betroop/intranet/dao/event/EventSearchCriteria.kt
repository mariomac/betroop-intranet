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

package es.betroop.intranet.dao

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.sql.Timestamp
import java.util.*

data class EventSearchCriteria(val id: Long? = null, val page: Long = 1, val fromTimestamp: Long? = null, val text: String? = null)
: SearchCriteria {
    companion object {
        val reader = jacksonObjectMapper().reader().forType(EventSearchCriteria::class.java)
        fun fromJson(json: String): EventSearchCriteria {

            val tree = reader.readValue<EventSearchCriteria>(json)
            return tree
        }
    }

    fun isVoid(): Boolean = id != null && fromTimestamp != null && text != null;


    override fun build(columnPrefix:String): Pair<String, ArrayList<Any>> {
        var criteriaList = ArrayList<String>()
        var args = ArrayList<Any>()
        if (id != null) {
            criteriaList.add("id = ?")
            args.add(id)
        }
        if (fromTimestamp != null) {
            criteriaList.add("event_time >= ?")
            args.add(Timestamp(fromTimestamp))
        }
        if (text != null && text.trim() != "") {
            criteriaList.add("(tag like ? OR name like ?)")
            val textLike = "%${text}%"
            args.add(textLike)
            args.add(textLike)
        }

        if (criteriaList.size > 0) {
            return Pair(" WHERE ${criteriaList.joinToString(" AND ")}", args)
        } else {
            return Pair("", args)
        }
    }

}