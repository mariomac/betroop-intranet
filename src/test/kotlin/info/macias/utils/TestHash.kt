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

package info.macias.utils

import es.betroop.intranet.utils.HashTool
import org.jetbrains.spek.api.Spek
import kotlin.test.assertEquals

class HashToolTest : Spek({
    describe("Hash Tool") {
        it("Should encode correctly in SHA256") {
            assertEquals("59195c6c541c8307f1da2d1e768d6f2280c984df217ad5f4c64c3542b04111a4".toLowerCase(),
                    HashTool.hash("mario").toLowerCase());
            assertEquals("b221d9dbb083a7f33428d7c2a3c3198ae925614d70210e28716ccaa7cd4ddb79".toLowerCase(),
                    HashTool.hash("hola").toLowerCase());
            assertEquals("32e508a8cc060856a414b6ee558b4cae0a20611141ed2f8adcb572326ae6b623".toLowerCase(),
                    HashTool.hash("que pasa colega").toLowerCase());
        }
    }
});