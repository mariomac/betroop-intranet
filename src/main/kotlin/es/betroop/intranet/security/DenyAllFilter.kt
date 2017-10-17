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

package es.betroop.intranet.security

import org.slf4j.LoggerFactory
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletResponse

/**
 * Filter that denies all the accesses. This is done to enhance security, restricting access to all the URLs
 * that are not specified in the shiro.ini [urls] section

 * @author Mario Macias (http://github.com/mariomac)
 */
class DenyAllFilter : Filter {
    val log = LoggerFactory.getLogger(DenyAllFilter::class.java)

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        log.debug("DenyAllFilter.init")
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        log.debug("DenyAllFilter.doFilter")

        val hr = servletResponse as HttpServletResponse
        hr.sendError(HttpServletResponse.SC_FORBIDDEN, "Access to this URL is forbidden for ALL the users")
    }

    override fun destroy() {

    }
}