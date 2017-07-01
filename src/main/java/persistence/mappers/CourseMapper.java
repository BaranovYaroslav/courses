package persistence.mappers;

import entities.Course;
import entities.CourseType;
import entities.Location;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ярослав on 15.04.2017.
 */
public class CourseMapper {
    public static Course map(ResultSet rs) throws SQLException{
        Course.Builder builder = Course.newBuilder();

        builder.setId(rs.getInt("id"))
               .setName(rs.getString("name"))
               .setDescription(rs.getString("description"))
               .setStartDate(rs.getString("start_date"))
               .setEndDate(rs.getString("end_date"))
               .setNumberOfStudents(rs.getInt("students_number"))
               .setPrice(rs.getInt("price"))
               .setFree(rs.getBoolean("is_free"))
               .setProfessor(getProfessor(rs))
               .setLocation(getLocation(rs))
               .setType(CourseType.valueOf(rs.getString("type").toUpperCase()));

        return builder.build();
    }

    private static User getProfessor(ResultSet rs) throws SQLException {
        User.Builder builder = User.newBuilder();
        builder.setId(rs.getInt("professor_id"));
        return builder.build();
    }

    private static Location getLocation(ResultSet rs) throws SQLException {
        Location.Builder builder = Location.newBuilder();

        builder.setCity(rs.getString("city"))
                .setAddress(rs.getString("address"))
                .setXCoordinate(rs.getDouble("x"))
                .setYCoordinate(rs.getDouble("y"));

        return builder.build();
    }

    private static void setStudentIndexes(Course course, ResultSet rs) throws SQLException {

    }
}
