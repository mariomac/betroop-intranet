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

package es.betroop.intranet.model

import java.net.URL

/**
 * Created by mmacias on 27/9/16.
 */
data class User(var id: Long? = null, var name: String, var email:String?=null,
                var createTime:Long?=null, var photoUrl:URL?=null, var image_modified: Boolean=false,
                var description:String?=null, var role: UserRole = UserRole.PARTICULAR)


enum class UserRole(val toInt: Int) {
    PARTICULAR(0), BAR(1), ALL(-1);

    companion object {
        fun fromInt(value: Int?) =
                when (value) {
                    0 -> PARTICULAR
                    1 -> BAR
                    else -> ALL
                }
    }
}