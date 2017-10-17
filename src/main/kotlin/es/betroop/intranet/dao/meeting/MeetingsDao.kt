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

import es.betroop.intranet.dao.Dao
import es.betroop.intranet.model.Meeting
import es.betroop.intranet.model.aux.MeetingLocation

/**
 * Created by mmacias on 28/9/16.
 */
interface MeetingsDao : Dao<Meeting, MeetingSearchCriteria> {
    fun getAddress(meetingId: Long): MeetingLocation?
    fun insertLocation(location: MeetingLocation)
    fun updateLocation(location: MeetingLocation)
    fun getLastLocationForOwner(ownerId: Long): MeetingLocation?
    fun assignOwnerToMeeting(meetingId: Long, ownerId: Long)
}