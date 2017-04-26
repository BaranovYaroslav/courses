package persistence.mappers;

import entities.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ярослав on 17.04.2017.
 */
public class RoleMapper {
    public static Role map(ResultSet rs) throws SQLException {
        return new Role(rs.getString("group"));
    }
}
