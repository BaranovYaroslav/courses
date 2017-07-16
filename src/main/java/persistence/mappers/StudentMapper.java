package persistence.mappers;

import entities.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ярослав on 16.07.2017.
 */
public class StudentMapper {

    public static Student map(ResultSet resultSet) throws SQLException {
        return new Student(UserMapper.map(resultSet));
    }
}
