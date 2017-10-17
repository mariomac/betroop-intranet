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

package es.betroop.intranet.controller

import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import es.betroop.intranet.beans.ApplicationContextProvider
import es.betroop.intranet.dao.EventSearchCriteria
import es.betroop.intranet.dao.event.EventsDao
import es.betroop.intranet.dao.meeting.MeetingSearchCriteria
import es.betroop.intranet.dao.meeting.MeetingsDao
import es.betroop.intranet.model.Event
import es.betroop.intranet.model.Meeting
import es.betroop.intranet.model.aux.MeetingLocation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller

/**
 * Created by mmacias on 28/9/16.
 */
@Controller
class MeetingsController {

    @Autowired
    internal open lateinit var dao: MeetingsDao

    @Autowired
    internal open lateinit var eventsDao: EventsDao

    @Value("\${db.page.entries}")
    var dbPageEntries: Int = 20



    val log = LoggerFactory.getLogger(MeetingsController::class.java)

    fun getAll(sc: MeetingSearchCriteria): List<Meeting> {
        return dao.find(sc)
    }

    fun get(meetingId: Long): Meeting? {
        val meetings = dao.find(searchCriteria = MeetingSearchCriteria(id = meetingId))
        return if (meetings.size == 0) null else meetings[0];
    }

    fun update(meeting: Meeting) {
        dao.update(meeting)
    }

    fun create(meeting: Meeting): Long {
        return dao.insert(meeting)
    }

    fun count(sc: MeetingSearchCriteria): Long {
        val ev = dao.count(sc)
        val pe = dbPageEntries
        return ((ev / pe) + (if (ev % pe == 0L) 0 else 1)).toLong()
    }

    fun updateLocation(location: MeetingLocation) {
        val previousAddres = dao.getAddress(location.meetingId!!)
        if(previousAddres == null) {
            dao.insertLocation(location)
        } else {
            dao.updateLocation(location)
        }
    }

    fun insertMultiMeeting(events: Collection<Long>,
                           owners: Collection<Long>,
                           startBefore: Int,
                           endAfter: Int,
                           meetingTemplate: Meeting) {
        events.forEach { eventId ->
            val storedEvent: Event = eventsDao.find(EventSearchCriteria(id=eventId)).first()
            owners.forEach { ownerId ->
                try {
                    insertNewMeeting(Meeting(
                            name = meetingTemplate.name,
                            eventId = eventId,
                            numAsistentes = meetingTemplate.numAsistentes,
                            type = meetingTemplate.type,
                            smoke = meetingTemplate.smoke,
                            alcohol = meetingTemplate.alcohol,
                            team = meetingTemplate.team,
                            description = meetingTemplate.description,
                            startTime = storedEvent.eventTime - 60 * 1000 * startBefore,
                            finishTime = storedEvent.eventTime + 60 * 1000 * endAfter,
                            tag = meetingTemplate.tag,
                            active = meetingTemplate.active,
                            extra = Meeting.Extra(ownerId = ownerId)
                    ))

                } catch(e: Exception) {
                    log.error("Error inserting a meeting for event $eventId and owner $ownerId", e)
                    log.debug("Meeting template: " + jacksonObjectMapper().writer().writeValueAsString(meetingTemplate))
                }
            }
        }
    }

    fun insertNewMeeting(meeting: Meeting) {
        val previousLocation = dao.getLastLocationForOwner(meeting.extra!!.ownerId!!)

        val newMeetingId = dao.insert(meeting)
        dao.assignOwnerToMeeting(newMeetingId, meeting.extra.ownerId!!)

        previousLocation?.apply {
            dao.insertLocation(MeetingLocation(
                    meetingId = newMeetingId,
                    street = street,
                    city = city,
                    postalCode = postalCode,
                    country = country,
                    latitude = latitude,
                    longitude = longitude
            ))
        }
    }

    fun getAddress(meetingId: Long): MeetingLocation? = dao.getAddress(meetingId)

}