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

package es.betroop.intranet.dao

import es.betroop.intranet.model.DbEvent
import es.betroop.intranet.model.Event
import es.betroop.intranet.model.EventType
import java.net.MalformedURLException
import java.net.URL
import java.sql.Timestamp

// TODO: cambiar a funciones que se añadan a las clases DbEvent y Event

internal fun DbEvent.toModel(): Event =
    Event(id,
            name,
            highlighting != 0,
            run {
                try {
                    URL(photoUrl)
                } catch (e:MalformedURLException) {
                    null
                }
            },
            tag,
            type,
            event_time!!.time,
            event_type?:EventType.OTROS.toInt)

internal fun Event.toDb() : DbEvent =
            DbEvent(id,
                    name,
                    if (highlighting) 1 else 0,
                    photoUrl?.toString(),
                    tag,
                    type,
                    Timestamp(eventTime),
                    event_type
            )
