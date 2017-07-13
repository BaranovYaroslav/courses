package persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that integer object course from database result set.
 *
 * @author Yaroslav Baranov
 */
public class StudentIndexMapper {
    public static Integer map(ResultSet rs) throws SQLException{
        return rs.getInt("student_id");
    }
}
