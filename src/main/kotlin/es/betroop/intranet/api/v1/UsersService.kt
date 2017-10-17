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
import es.betroop.intranet.controller.UsersController
import es.betroop.intranet.dao.meeting.UserSearchCriteria
import es.betroop.intranet.model.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Component
@Path("/v1/users")
class UsersService {

    @Autowired
    open lateinit var controller: UsersController

    val jsonWriter: ObjectWriter
    val jsonReader: ObjectReader

    init {
        val mapper = jacksonObjectMapper()
        jsonWriter = mapper.writer()
        jsonReader = mapper.reader()
    }

    var log = LoggerFactory.getLogger(UsersService::class.java)
    /**
     * Returns the id of the created meeting
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun create(userJson:String):Long {
        log.debug("Create: $userJson")
        var user = jacksonObjectMapper().readValue(userJson,User::class.java) // jsonReader.readValue<Event>(userJson)
        return controller.create(user)
    }

    @POST
    @Path("/query")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(searchCriteriaJson:String):String {
        log.debug("getAll searchCriteria = $searchCriteriaJson")
        val searchCriteria = UserSearchCriteria.fromJson(searchCriteriaJson)
        val ret = jsonWriter.writeValueAsString(controller.getAll(searchCriteria))
        log.debug("returning: $ret")
        return ret
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathParam("id") userId: Long): String {
        val user = controller.get(userId) ?: throw WebApplicationException(Response.Status.NOT_FOUND)
        return jsonWriter.writeValueAsString(user)
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun update(@PathParam("id") userId: Long, userJson:String) {
        val user = jacksonObjectMapper().readValue(userJson,User::class.java) // jsonReader.readValue<Event>(userJson)
        if(userId != user.id) throw WebApplicationException("Path param meeting id ("+ userId +") does not coincide with request body id (" + user.id +")");
        controller.update(user);
    }

    @POST
    @Path("/pages")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    fun count(searchCriteriaJson:String) : String {
        val searchCriteria = UserSearchCriteria.fromJson(searchCriteriaJson)
        return controller.count(searchCriteria).toString()
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    fun hello():String = "Hello"
}