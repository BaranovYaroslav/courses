package security;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class ResourceToRoleMapper {

    private Logger LOGGER = Logger.getLogger(ResourceToRoleMapper.class);

    private static ResourceToRoleMapper instance;

    private Map<String, String> matches = new ConcurrentHashMap<>();

    private ResourceToRoleMapper() {
    }

    public static ResourceToRoleMapper getInstance() {
        if(instance == null) {
            instance = new ResourceToRoleMapper();
        }

        return instance;
    }

    public ResourceToRoleMapper appendMapping(String url, String role) {
        matches.put(url, role);
        return this;
    }

    public String getAccessibleRoleForUrl(String url) {
        for(Map.Entry entry: matches.entrySet()) {
            if(url.contains((String) entry.getKey())) {
                return (String) entry.getValue();
            }
        }

        return null;
    }

    public Map<String, String> getMatches() {
        return matches;
    }

    public void setMatches(Map<String, String> matches) {
        this.matches = matches;
    }
}
