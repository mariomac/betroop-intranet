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

/**
 * Created by mmacias on 27/9/16.
 */
interface Dao<T,SC:SearchCriteria> {

    fun find(searchCriteria: SC) : List<T>
    fun count(sc: SC) : Long
    fun update(value : T)
    fun insert(event: T):Long
}