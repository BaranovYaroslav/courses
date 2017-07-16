package persistence.mappers;

import constants.DatabaseColumn;
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

        builder.setId(rs.getInt(DatabaseColumn.ID))
               .setLogin(rs.getString(DatabaseColumn.LOGIN))
               .setFullName(rs.getString(DatabaseColumn.FULL_NAME))
               .setEmail(rs.getString(DatabaseColumn.EMAIL))
               .setPassword(rs.getString(DatabaseColumn.PASSWORD))
               .setRole(new Role(rs.getString(DatabaseColumn.GROUP)));

        return  builder.build();
    }
}
