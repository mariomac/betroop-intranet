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
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import es.betroop.intranet.controller.EventsController
import es.betroop.intranet.dao.EventSearchCriteria
import es.betroop.intranet.model.Event
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Component
@Path("/v1/events")
class EventsService {

    @Autowired
    open lateinit var controller: EventsController //= EventsController()

    val jsonWriter: ObjectWriter
    val jsonReader: ObjectReader

    init {
        val mapper = jacksonObjectMapper()
        jsonWriter = mapper.writer()
        jsonReader = mapper.reader()
    }

    var log = LoggerFactory.getLogger(EventsService::class.java)
    /**
     * Returns the id of the created meeting
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun create(eventJson:String):Long {
        log.debug("Create: $eventJson")
        var event = jacksonObjectMapper().readValue(eventJson,Event::class.java) // jsonReader.readValue<Event>(userJson)
        return controller.create(event)
    }

    @POST
    @Path("/query")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(searchCriteriaJson:String):String {
        val searchCriteria = EventSearchCriteria.fromJson(searchCriteriaJson)
        return jsonWriter.writeValueAsString(controller.getAll(searchCriteria))
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getEvent(@PathParam("id") eventId: Long): String {
        val event = controller.get(eventId) ?: throw WebApplicationException(Response.Status.NOT_FOUND)
        return jsonWriter.writeValueAsString(event)
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun update(@PathParam("id") eventId: Long, eventJson:String) {
        val event = jacksonObjectMapper().readValue(eventJson,Event::class.java) // jsonReader.readValue<Event>(userJson)
        if(eventId != event.id) throw WebApplicationException("Path param meeting id ("+eventId+") does not coincide with request body id (" + event.id +")");
        controller.update(event);
    }

    @POST
    @Path("/pages")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun count(searchCriteriaJson:String) : String {
        val searchCriteria = EventSearchCriteria.fromJson(searchCriteriaJson)
        return controller.count(searchCriteria).toString()
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    fun hello():String = "Hello"
}