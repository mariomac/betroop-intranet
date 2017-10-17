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

package es.betroop.intranet.dao.user

import es.betroop.intranet.model.User
import es.betroop.intranet.model.UserRole
import java.net.URL
import java.sql.Timestamp

/**
 * Created by mmacias on 1/10/16.
 */

internal fun DbUser.toModel(): User {
    var url = try {
        URL(photoUrl)
    } catch (e:Exception) {
        null
    }
    return User(id, name, email,
            if (createTime == null) null else (createTime as Timestamp).time,
            url,
            imageModified != 0,
            description,
            UserRole.fromInt(role)
    )
}

internal fun User.toDb(): DbUser =
        DbUser(
                id = id,
                name = name,
                email = email,
                createTime = if (createTime == null) null else Timestamp(createTime!!),
                photoUrl = if (photoUrl == null) null else photoUrl!!.toString(),
                imageModified = if (image_modified) 1 else 0,
                description = description,
                role = role.toInt
        )


