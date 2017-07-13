package persistence.mappers;

import entities.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that extract user role from database result set.
 *
 * @author Yaroslav Baranov
 */
public class RoleMapper {
    public static Role map(ResultSet rs) throws SQLException {
        return new Role(rs.getString("group"));
    }
}
