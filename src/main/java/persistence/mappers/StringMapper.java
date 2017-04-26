package persistence.mappers;

import entities.Course;
import entities.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ярослав on 16.04.2017.
 */
public class StringMapper {
    public static String map(ResultSet rs) throws SQLException {
        return rs.getString("group");
    }
}
