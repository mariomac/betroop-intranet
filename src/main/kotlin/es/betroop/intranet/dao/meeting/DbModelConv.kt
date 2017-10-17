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

package es.betroop.intranet.dao.meeting

import es.betroop.intranet.model.Meeting
import es.betroop.intranet.model.MeetingType
import java.sql.Timestamp

internal fun DbMeeting.toModel() : Meeting =
        Meeting(
                id = id,
                name = name,
                eventId = eventId,
                numAsistentes = numAsistentes,
                type = MeetingType.fromInt(type),
                smoke = smoke != 0,
                alcohol = alcohol != 0,
                team = team,
                description = description,
                startTime = startTime.time,
                finishTime = finishTime.time,
                tag = tag,
                latitude = latitude,
                longitude = longitude,
                active = active != 0,
                extra = Meeting.Extra(
                        ownerId = extra?.ownerId,
                        ownerName = extra?.ownerName,
                        eventName = extra?.eventName
                        )
        )

internal fun Meeting.toDb() : DbMeeting =
        DbMeeting(
                id = id,
                name = name,
                eventId = eventId,
                numAsistentes = numAsistentes,
                type = type.type,
                smoke = if(smoke) 1 else 0,
                alcohol = if(alcohol) 1 else 0,
                team = team,
                description = description,
                startTime = Timestamp(startTime),
                finishTime = Timestamp(finishTime),
                tag = tag,
                latitude = latitude,
                longitude = longitude,
                active = if(active) 1 else 0,
                extra = DbMeeting.Extra(
                        ownerId = extra?.ownerId,
                        ownerName = extra?.ownerName,
                        eventName = extra?.eventName
                )

        )