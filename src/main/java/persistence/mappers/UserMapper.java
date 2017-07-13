package persistence.mappers;

import entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that extract user from database result set.
 *
 * @author Yaroslav Baranov
 */
public class UserMapper {

    public static User map(ResultSet rs) throws SQLException{
        User.Builder builder = User.newBuilder();

        builder.setId(rs.getInt("id"))
               .setLogin(rs.getString("login"))
               .setFullName(rs.getString("full_name"))
               .setEmail(rs.getString("email"))
               .setPassword(rs.getString("password"))
               .setRole(new Role(rs.getString("group")));

        return  builder.build();
    }
}
