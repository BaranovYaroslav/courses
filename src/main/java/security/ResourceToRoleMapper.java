package security;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import entities.UserRole;
import org.apache.log4j.Logger;

/**
 * Holder of mapping between user role and accessible resources for this role.
 *
 * @author Yaroslav Baranov
 */
public class ResourceToRoleMapper {

    private Logger LOGGER = Logger.getLogger(ResourceToRoleMapper.class);

    private static ResourceToRoleMapper instance;

    private Map<String, UserRole> matches = new ConcurrentHashMap<>();

    private ResourceToRoleMapper() {
    }

    public static ResourceToRoleMapper getInstance() {
        if(instance == null) {
            instance = new ResourceToRoleMapper();
        }

        return instance;
    }

    public ResourceToRoleMapper addConstrains(String url, UserRole role) {
        matches.put(url, role);
        return this;
    }

    public UserRole getAccessibleRoleForUrl(String url) {
        for(Map.Entry entry: matches.entrySet()) {
            if(url.contains((String) entry.getKey())) {
                return (UserRole) entry.getValue();
            }
        }

        return null;
    }

    public Map<String, UserRole> getMatches() {
        return matches;
    }

    public void setMatches(Map<String, UserRole> matches) {
        this.matches = matches;
    }
}
