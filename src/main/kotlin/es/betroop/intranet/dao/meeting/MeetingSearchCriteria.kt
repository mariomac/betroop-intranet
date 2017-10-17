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
import java.util.*

/**
 * Created by mmacias on 27/9/16.
 */
class MeetingSearchCriteria(
        val page: Int = 1,
        val id: Long? = null,
        val userId: Long? = null
    ) : SearchCriteria {
    companion object {
        val reader = jacksonObjectMapper().reader().forType(MeetingSearchCriteria::class.java)
        fun fromJson(json: String): MeetingSearchCriteria {
            val tree = reader.readValue<MeetingSearchCriteria>(json)
            return tree
        }
    }

    override fun build(columnPrefix:String): Pair<String, ArrayList<Any>> {
        var criteriaList = ArrayList<String>()
        var args = ArrayList<Any>()
        if (id != null) {
            criteriaList.add("${columnPrefix}id = ?")
            args.add(id)
        }
        if (userId != null) {
            criteriaList.add("${columnPrefix}userId = ?")
            args.add(userId)
        }

        if (criteriaList.size > 0) {
            return Pair(" AND ${criteriaList.joinToString(" AND ")}", args)
        } else {
            return Pair("", args)
        }
    }
}