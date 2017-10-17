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

import java.util.*

interface SearchCriteria {
    /**
     * @return Pair whose first element is the SQL query string (starting by "WHERE"), or an empty
     * string in case no search criteria apply, and a list of arguments that correspond to each
     * value to pass to the SQL command chriteria
     */
    fun build(columnPrefix:String=""): Pair<String, ArrayList<Any>>
}