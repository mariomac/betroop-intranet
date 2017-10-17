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

package es.betroop.intranet.utils

import java.security.MessageDigest
import javax.xml.bind.annotation.adapters.HexBinaryAdapter

/**
 * SHA-256 hasher wrapper
 */
object HashTool {
    val hashinAlgorithm = "SHA-256";

    fun hash(string:String): String {
        val digester = MessageDigest.getInstance(hashinAlgorithm)
        val stringBytes = string.toByteArray(Charsets.UTF_8)
        return HexBinaryAdapter().marshal(digester.digest(stringBytes))
    }
}