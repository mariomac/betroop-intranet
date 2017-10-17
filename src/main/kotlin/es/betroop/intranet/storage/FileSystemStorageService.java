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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * A [StorageService] that stores all the files in a simple local file system.
 */
@Service
public class FileSystemStorageService implements StorageService {

    private static Logger LOG = LoggerFactory.getLogger(FileSystemStorageService.class);

    private final Path rootLocation;

    @Value("${files.max.mb}")
    Long maxSizeMb;

    @Value("${files.url.path}")
    String urlPath;

    private static final String PHOTO_SUFFIX = ".png";

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String store(InputStream file, FormDataContentDisposition fileDetail) {
        if(fileDetail.getSize() > maxSizeMb * 1024 * 1024) {
            throw new StorageException("Archivo demasiado grande");
        }
        String newName = UUID.randomUUID() + PHOTO_SUFFIX;
        try {
            LOG.debug("renaming photo as {}", newName);
            Files.copy(file, this.rootLocation.resolve(newName));
            return urlPath + newName;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + newName, e);
        }
    }

    private Path pathFor(String filename) {
        return rootLocation.resolve(filename);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = pathFor(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch(FileAlreadyExistsException e) {
            LOG.debug("File already exists during creation: {}. Continuing normally", e.getMessage());
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
