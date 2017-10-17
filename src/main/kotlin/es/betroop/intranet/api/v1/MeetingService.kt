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

package es.betroop.intranet.api.v1

import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.node.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import es.betroop.intranet.controller.MeetingsController
import es.betroop.intranet.dao.meeting.MeetingSearchCriteria
import es.betroop.intranet.model.Meeting
import es.betroop.intranet.model.aux.MeetingLocation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Component
@Path("/v1/meetings")
class MeetingService {

    @Autowired
    open lateinit var controller: MeetingsController

    val jsonWriter: ObjectWriter
    val jsonReader: ObjectReader

    init {
        val mapper = jacksonObjectMapper()
        jsonWriter = mapper.writer()
        jsonReader = mapper.reader()
    }

    var log = LoggerFactory.getLogger(MeetingService::class.java)

    @POST
    @Path("/query")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(searchCriteriaJson:String):String {
        log.debug("getAll searchCriteria = $searchCriteriaJson")
        val searchCriteria = MeetingSearchCriteria.fromJson(searchCriteriaJson)
        val ret = jsonWriter.writeValueAsString(controller.getAll(searchCriteria))
        log.debug("returning: $ret")
        return ret
    }

    @PUT
    @Path("/location")
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateLocation(meetingLocationJson:String) {
        log.debug("Update location for meeting ${meetingLocationJson}")
        val loc = jsonReader.forType(MeetingLocation::class.java)
                .readValue<MeetingLocation>(meetingLocationJson)
        controller.updateLocation(loc)
    }
    @GET
    @Path("/{id}/location")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAddress(@PathParam("id") meetingId:String): String? =
        controller.getAddress(meetingId.toLong())?.let {
            jsonWriter.forType(MeetingLocation::class.java).writeValueAsString(it)
        }


    @POST
    @Path("/pages")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun count(searchCriteriaJson:String) : String {
        val searchCriteria = MeetingSearchCriteria.fromJson(searchCriteriaJson)
        return controller.count(searchCriteria).toString()
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    fun updateMeeting(meetingJson:String) {
        val meeting = jsonReader.forType(Meeting::class.java)
                        .readValue<Meeting>(meetingJson)
        return controller.update(meeting)
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun insertMeeting(multiMeetingJson:String) {
        log.debug("Insertmeeting called: $multiMeetingJson")

        val json = jsonReader.readTree(multiMeetingJson) as ObjectNode
        val events = (json.remove("events") as ArrayNode).map { it.longValue() }
        val owners = (json.remove("owners") as ArrayNode).map { it.longValue() }
        val startBefore = (json.remove("startBefore") as ValueNode).intValue()
        val endAfter = (json.remove("endAfter") as NumericNode).intValue()
        val meetingTemplate = jsonReader.forType(Meeting::class.java)
                        .readValue<Meeting>(json)
        controller.insertMultiMeeting(events,owners,startBefore,endAfter,meetingTemplate)
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    fun hello():String = "Hello"
}