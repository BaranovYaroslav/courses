package persistence.mappers;

import entities.Course;
import entities.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that extract string object from database result set.
 *
 * @author Yaroslav Baranov
 */
public class StringMapper {
    public static String map(ResultSet rs) throws SQLException {
        return rs.getString("group");
    }
}
