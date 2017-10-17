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

import es.betroop.intranet.storage.StorageService
import org.glassfish.jersey.media.multipart.FormDataContentDisposition
import org.glassfish.jersey.media.multipart.FormDataParam
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.InputStream
import javax.ws.rs.*
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Component
@Path("/v1/files")
class FileService @Autowired constructor(val storageService: StorageService) {

    private val log = LoggerFactory.getLogger(FileService::class.java)
    @GET
    @Path("/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun get(@PathParam("filename") filename: String): Response {
        val file = storageService.loadAsResource(filename)

        return Response.
                ok(file.inputStream)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"${file.filename}\""
                ).build()
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    open fun post(
            @FormDataParam("file") uploadedInputStream: InputStream,
            @FormDataParam("file") fileDetail: FormDataContentDisposition) : String {
        log.debug("fileDetail.name = {}",fileDetail.name)
        log.debug("fileDetail.type = {}",fileDetail.type)
        log.debug("fileDetail.size = {}",fileDetail.size)
        log.debug("fileDetail.parameters = {}"+fileDetail.parameters)
        return storageService.store(uploadedInputStream,fileDetail)
    }

}
