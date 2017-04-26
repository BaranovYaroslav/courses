package persistence.mappers;

import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ярослав on 15.04.2017.
 */
public class UserMapper {

    public static User map(ResultSet rs) throws SQLException{
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        return user;
    }
}
