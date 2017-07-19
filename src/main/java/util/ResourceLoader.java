package util;

import constants.LoggerMessage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * Util class that load properties.
 *
 * @author Yaroslav Baranov
 */
public class ResourceLoader {

    private static Logger LOGGER = Logger.getLogger(ResourceLoader.class);

    /**
     * Method that create object of properties.
     *
     * @param path path to properties file
     * @return object of Properties class
     */
    public static Properties fromFile(String path) {
        Properties properties = new Properties();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try(InputStream resourceStream = loader.getResourceAsStream(path)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static File getFile(String path) {
        URL url = ResourceLoader.class.getClassLoader().getResource(path);

        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            LOGGER.warn(LoggerMessage.ON_BAD_PATH_MESSAGE + e);
            return null;
        }

        if (file.exists()) {
            return file;
        }

        LOGGER.warn(LoggerMessage.ON_UNRESOLVED_RESOURCE_MESSAGE);
        return null;
    }
}

