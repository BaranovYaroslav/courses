package persistence.mappers;

import constants.DatabaseColumn;
import entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Class that extract course from database result set.
 *
 * @author Yaroslav Baranov
 */
public class CourseMapper {
    public static Course map(ResultSet rs) throws SQLException{
        Course.Builder builder = Course.newBuilder();

        builder.setId(rs.getInt(DatabaseColumn.ID))
               .setName(rs.getString(DatabaseColumn.COURSE_NAME))
               .setDescription(rs.getString(DatabaseColumn.COURSE_DESCRIPTION))
               .setStartDate(LocalDate.parse(rs.getString(DatabaseColumn.START_DATE)))
               .setEndDate(LocalDate.parse(rs.getString(DatabaseColumn.END_DATE)))
               .setNumberOfStudents(rs.getInt(DatabaseColumn.STUDENTS_NUMBER))
               .setPrice(rs.getInt(DatabaseColumn.PRICE))
               .setFree(rs.getBoolean(DatabaseColumn.IS_FREE))
               .setProfessor(getProfessor(rs))
               .setLocation(getLocation(rs))
               .setType(CourseType.valueOf(rs.getString(DatabaseColumn.TYPE).toUpperCase()));

        return builder.build();
    }

    private static User getProfessor(ResultSet rs) throws SQLException {
        User.Builder builder = User.newBuilder();

        builder.setId(rs.getInt(DatabaseColumn.PROFESSOR_ID))
               .setFullName(rs.getString(DatabaseColumn.FULL_NAME))
               .setEmail(rs.getString(DatabaseColumn.EMAIL))
               .setPassword(rs.getString(DatabaseColumn.PASSWORD))
               .setLogin(rs.getString(DatabaseColumn.LOGIN))
               .setRole(UserRole.valueOf(rs.getString(DatabaseColumn.GROUP).toUpperCase()));

        return builder.build();
    }

    private static Location getLocation(ResultSet rs) throws SQLException {
        Location.Builder builder = Location.newBuilder();

        builder.setId(rs.getInt(DatabaseColumn.LOCATION_ID))
               .setCity(rs.getString(DatabaseColumn.CITY))
               .setAddress(rs.getString(DatabaseColumn.ADDRESS))
               .setXCoordinate(rs.getDouble(DatabaseColumn.X_COORDINATE))
               .setYCoordinate(rs.getDouble(DatabaseColumn.Y_COORDINATE));

        return builder.build();
    }
}
