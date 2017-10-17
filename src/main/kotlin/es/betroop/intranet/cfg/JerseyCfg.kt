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

package es.betroop.intranet.cfg

import es.betroop.intranet.api.v1.EventsService
import es.betroop.intranet.api.v1.FileService
import es.betroop.intranet.api.v1.MeetingService
import es.betroop.intranet.api.v1.UsersService
import org.glassfish.jersey.media.multipart.MultiPartFeature
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration
import javax.ws.rs.ApplicationPath

/**
 * Created by mmacias on 15/8/16.
 */
@Configuration
@ApplicationPath("/api")
open class JerseyCfg : ResourceConfig() {
    init {
        registerEndpoints()
    }

    private fun registerEndpoints() {
        register(MultiPartFeature::class.java)
        register(EventsService::class.java)
        register(UsersService::class.java)
        register(FileService::class.java)
        register(MeetingService::class.java)
    }
}