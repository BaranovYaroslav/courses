package service;

import com.sun.javafx.collections.MappingChange;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holder of services.
 *
 * @author Yaroslav Baranov
 */
public class ServiceLoader {

    private Logger LOGGER = Logger.getLogger(ServiceLoader.class);

    private static ServiceLoader instance;

    private Map<String, Object> loadedServices;

    private ServiceLoader() {
        loadedServices = new ConcurrentHashMap<>();
    }

    public static ServiceLoader getInstance () {
        if(instance == null) {
            instance = new ServiceLoader();
        }

        return instance;
    }

    /**
     * Method that load given service.
     *
     * @param clazz class of service to be loaded
     */
    public <T> void loadService(Class<T> clazz) {
        try {
            loadedServices.put(clazz.getSimpleName(), clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("Error in initializing of service: " + e);
        }
    }

    /**
     * Method that load given service.
     *
     * @param clazz class of service to be loaded
     * @param o object of given class
     */
    public <T> void loadService(Class<T> clazz, T o) {
        loadedServices.put(clazz.getSimpleName(), o);
    }

    /**
     * Method that return object of needed service.
     *
     * @param clazz class of service to be returned
     * @return object of service
     */
    public <T> T getService(Class<T> clazz) {
        Object serviceObject = loadedServices.get(clazz.getSimpleName());
        return clazz.cast(serviceObject);
    }

    public Map<String, Object> getLoadedServices() {
        return loadedServices;
    }

    public void setLoadedServices(Map<String, Object> loadedServices) {
        this.loadedServices = loadedServices;
    }
}
