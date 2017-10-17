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

data class Event(var id:Long?, var name:String?, var highlighting:Boolean = false, var photoUrl:URL?, var tag:String?, var type:String?, var eventTime:Long = 0,var event_type:Int) {

}

enum class EventType(val toInt: Int) {
    FUTBOL(1), BALONCESTO(2), PADEL(3), TENIS(4), MOTOS(5), F1(6), OTROS(7);

    companion object {
        fun fromInt(value:Int?) =
                when (value) {
                    1 -> FUTBOL
                    2 -> BALONCESTO
                    3 -> PADEL
                    4 -> TENIS
                    5 -> MOTOS
                    6 -> F1
                    else -> OTROS
                }
    }
}