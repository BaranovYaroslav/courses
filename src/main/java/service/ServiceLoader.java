package service;

import com.sun.javafx.collections.MappingChange;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ярослав on 13.04.2017.
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

    public <T> void loadService(Class<T> clazz) {
        try {
            loadedServices.put(clazz.getSimpleName(), clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("Error in initializing of service: " + e);
        }
    }

    public <T> void loadService(Class<T> clazz, T o) {
        loadedServices.put(clazz.getSimpleName(), o);
    }

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
