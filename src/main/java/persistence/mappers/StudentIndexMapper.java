package persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ярослав on 15.04.2017.
 */
public class StudentIndexMapper {
    public static Integer map(ResultSet rs) throws SQLException{
        return rs.getInt("student_id");
    }
}
