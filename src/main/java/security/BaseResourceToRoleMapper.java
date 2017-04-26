package security;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ярослав on 19.04.2017.
 */
public class BaseResourceToRoleMapper {

    private static BaseResourceToRoleMapper instance;

    private Map<String, String> baseResourceToRole = new ConcurrentHashMap<>();

    private BaseResourceToRoleMapper() {}

    public static BaseResourceToRoleMapper getInstance() {
        if(instance == null) {
            instance = new BaseResourceToRoleMapper();
        }

        return instance;
    }

    public BaseResourceToRoleMapper addMapping(String role, String url) {
        baseResourceToRole.put(role, url);
        return instance;
    }

    public String getBaseUrlForRole(String role) {
        return baseResourceToRole.get(role);
    }
}
