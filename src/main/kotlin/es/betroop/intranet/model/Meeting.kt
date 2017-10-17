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

package es.betroop.intranet.model

import es.betroop.intranet.model.aux.MeetingLocation

/**
 * Created by mmacias on 27/9/16.
 */
data class Meeting(
        val id: Long? = null,
        val name: String,
        val eventId: Long,
        val numAsistentes: Int,
        val type: MeetingType = MeetingType.BAR,
        val smoke: Boolean = false,
        val alcohol: Boolean = true,
        val team: String = defaultTeamValue,
        val description: String = "",
        val startTime: Long,
        val finishTime: Long,
        val tag: String = "",
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val active: Boolean = true,
        val extra: Extra? = null) {

    companion object {
        val defaultTeamValue = "--"
        val statusOwner = 4
    }

    data class Extra(val ownerId: Long? = null,
                     val ownerName: String? = null,
                     val eventName : String? = null,
                     var location: MeetingLocation? = null
                     )
}


enum class MeetingType(val type: Int) {
    HOME(0), BAR(1), LIVE(1);

    companion object {
        fun fromInt(value: Int?) =
                when (value) {
                    1 -> BAR
                    2 -> LIVE
                    else -> HOME
                }
    }
}