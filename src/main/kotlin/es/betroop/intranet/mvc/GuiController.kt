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

package es.betroop.intranet.mvc

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * Created by mmacias on 16/8/16.
 */
@Controller
@RequestMapping("/gui")
open class GuiController : WebMvcConfigurerAdapter() {
    val log = LoggerFactory.getLogger(GuiController::class.java)

    @RequestMapping
    fun default() : String {
        log.info("redirecting to /gui/")
        return "redirect:/gui/"
    }

    @RequestMapping(path = arrayOf("/","/events"), method= arrayOf(RequestMethod.GET))
    fun events(): String {
        log.info("call to /events")
        return "events";
    }

    @RequestMapping(path = arrayOf("/users"), method = arrayOf(RequestMethod.GET))
    fun users(): String {
        log.info("call to /users")
        return "users"
    }

    @RequestMapping(path = arrayOf("/meetings"), method = arrayOf(RequestMethod.GET))
    fun meetings(): String {
        log.info("call to /meetings")
        return "meetings"
    }
}