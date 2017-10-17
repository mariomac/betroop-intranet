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

import java.sql.Timestamp
import java.util.*

internal data class DbEvent(
        var id:Long?=null,
        var name:String?=null,
        var highlighting:Int = 0,
        var photoUrl:String? = null,
        var tag:String? = null,
        var type:String? = null,
        var event_time:Timestamp? = null,
        var event_type: Int? = null) {
}