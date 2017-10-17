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

import es.betroop.intranet.model.Meeting
import es.betroop.intranet.model.MeetingType
import java.sql.Timestamp

/**
 * Created by mmacias on 27/9/16.
 */
internal data class DbMeeting(val id: Long? = null,
                              val name: String,
                              val eventId: Long,
                              val numAsistentes: Int,
                              val type: Int = MeetingType.BAR.type,
                              val smoke: Int = 0,
                              val alcohol: Int = 1,
                              val team: String = Meeting.defaultTeamValue,
                              val description: String = "",
                              val startTime: Timestamp,
                              val finishTime: Timestamp,
                              val tag: String = "",
                              val latitude: Double = 0.0,
                              val longitude: Double = 0.0,
                              val active: Int = 1,
                              val extra: Extra? = null) {
    data class Extra(val ownerId: Long?=null,
                     val ownerName: String? = null,
                     val eventName : String? = null)
}

