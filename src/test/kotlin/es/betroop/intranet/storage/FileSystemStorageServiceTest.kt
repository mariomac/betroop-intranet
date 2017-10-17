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

package es.betroop.intranet.storage

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.glassfish.jersey.media.multipart.FormDataContentDisposition
import org.jetbrains.spek.api.Spek
import org.mockito.Mockito.mock
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream

class FileSystemStorageServiceTest : Spek({

    val temporaryPath = File.createTempFile("betroop", "tests")
    temporaryPath.mkdir()
    val storageProperties = StorageProperties()
    storageProperties.location = temporaryPath.absolutePath

    given("A File System Storage service") {
        val storage = FileSystemStorageService(storageProperties)

        on("store") {
            ByteArrayInputStream(byteArrayOf(1, 2, 3, 4, 5)).use { file ->

            }
            storage.store()
        }

        on("storing files larger than admitted") {
            it("throws a StorageException") {
                assertThatThrownBy(storage.store(
                        mock(InputStream.class),
                        mock(FormDataContentDisposition.class)))
            }
        }
    }
    describe("File System storage") {
        val storage = FileSystemStorageService(object : StorageProperties() {
            override fun getLocation(): String {
                return super.getLocation()
            }

            override fun setLocation(location: String?) {
                super.setLocation(location)
            }
        })
        it("Should store and retireve multiple files") {
            storage
        }
    }
})