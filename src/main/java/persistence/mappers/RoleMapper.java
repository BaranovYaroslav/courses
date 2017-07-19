package persistence.mappers;

import constants.DatabaseColumn;
import entities.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that extract user role from database result set.
 *
 * @author Yaroslav Baranov
 */
public class RoleMapper {
    public static UserRole map(ResultSet rs) throws SQLException {
        return UserRole.valueOf(rs.getString(DatabaseColumn.GROUP).toUpperCase());
    }
}
