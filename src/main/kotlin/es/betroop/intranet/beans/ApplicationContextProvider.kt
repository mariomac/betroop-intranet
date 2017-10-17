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

package es.betroop.intranet.beans

import es.betroop.intranet.dao.event.EventsDao
import es.betroop.intranet.dao.webadmin.WebAdminDao
import es.betroop.intranet.dao.webadmin.impl.MySqlEventsDao
import es.betroop.intranet.dao.webadmin.impl.MySqlWebAdminDao
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ApplicationContextProvider : ApplicationContextAware {

    companion object {
        lateinit var appContext: ApplicationContext
    }

    override fun setApplicationContext(applicationContext: ApplicationContext?) {
        appContext = applicationContext!!
    }

    @Bean
    open fun webAdminDao() : WebAdminDao = MySqlWebAdminDao()

    @Bean
    open fun eventsDao() : EventsDao = MySqlEventsDao()
}