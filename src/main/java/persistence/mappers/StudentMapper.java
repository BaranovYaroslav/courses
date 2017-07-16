package persistence.mappers;

import entities.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that extract student from database result set.
 *
 * @author Yaroslav Baranov
 */
public class StudentMapper {

    public static Student map(ResultSet resultSet) throws SQLException {
        return new Student(UserMapper.map(resultSet));
    }
}
