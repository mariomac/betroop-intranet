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

package es.betroop.intranet.storage;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.core.io.Resource;

import java.io.InputStream;

/**
 * Interface for a Storage Service: an entity that allows storing/reading files (e.g. images) from the users.
 */
public interface StorageService {

    /**
     * Starts the service.
     */
    void init();

    /**
     * Stores a file
     * @param file File contents.
     * @param fileDetail Details of the submitted Form data.
     * @return The public URL to access the file.
     */
    String store(InputStream file, FormDataContentDisposition fileDetail);

    /**
     * Loads a file as a Spring [Resource] instance.
     * @param filename Name of the file.
     * @return The [Resource] that handles the file.
     */
    Resource loadAsResource(String filename);
}
