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

import es.betroop.intranet.beans.ApplicationContextProvider
import es.betroop.intranet.dao.EventSearchCriteria
import es.betroop.intranet.dao.event.EventsDao
import es.betroop.intranet.model.Event
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class EventsController {

    private fun dao() = ApplicationContextProvider.appContext.getBean("eventsDao", EventsDao::class.java)

    @Value("\${db.page.entries}")
    var dbPageEntries : Int = 20

    fun getAll(sc: EventSearchCriteria):List<Event> {
        return dao().find(sc)
    }

    fun get(eventId: Long): Event? {
        val events = dao().find(searchCriteria = EventSearchCriteria(eventId))
        return if(events.size == 0) null else events[0];
    }

    fun update(event:Event) {
        dao().update(event)
    }

    fun create(event:Event): Long {
        return dao().insert(event)
    }

    fun count(sc: EventSearchCriteria) : Long {
        val ev = dao().count(sc)
        val pe = dbPageEntries
        return (( ev / pe ) + (if(ev%pe == 0L) 0 else 1)).toLong()
    }
}
