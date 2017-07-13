package util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

/**
 * Util class that load properties.
 *
 * @author Yaroslav Baranov
 */
public class PropertiesLoader {

    private static Logger LOGGER = Logger.getLogger(PropertiesLoader.class);

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
}
