package security;

import entities.UserRole;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holder of mapping between user role and base resources for this role.
 *
 * @author Yaroslav Baranov
 */
public class BaseResourceToRoleMapper {

    private static BaseResourceToRoleMapper instance;

    private Map<UserRole, String> baseResourceToRole = new ConcurrentHashMap<>();

    private BaseResourceToRoleMapper() {}

    public static BaseResourceToRoleMapper getInstance() {
        if(instance == null) {
            instance = new BaseResourceToRoleMapper();
        }

        return instance;
    }

    public BaseResourceToRoleMapper addMapping(UserRole role, String url) {
        baseResourceToRole.put(role, url);
        return instance;
    }

    public String getBaseUrlForRole(UserRole role) {
        return baseResourceToRole.get(role);
    }
}
