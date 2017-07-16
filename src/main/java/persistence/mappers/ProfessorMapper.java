package persistence.mappers;

import entities.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ярослав on 16.07.2017.
 */
public class ProfessorMapper {

    public static Professor map(ResultSet resultSet) throws SQLException {
        return new Professor(UserMapper.map(resultSet));
    }
}
