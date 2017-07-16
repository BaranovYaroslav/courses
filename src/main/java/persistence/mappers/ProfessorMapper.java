package persistence.mappers;

import entities.Professor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that extract professor from database result set.
 *
 * @author Yaroslav Baranov
 */
public class ProfessorMapper {

    public static Professor map(ResultSet resultSet) throws SQLException {
        return new Professor(UserMapper.map(resultSet));
    }
}
