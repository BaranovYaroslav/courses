package persistence.mappers;

import entities.Course;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ярослав on 15.04.2017.
 */
public class CourseMapper {
    public static Course map(ResultSet rs) throws SQLException{
        Course course = new Course();

        course.setId(rs.getInt("id"));
        course.setName(rs.getString("name"));
        course.setDescription(rs.getString("description"));
        course.setStartDate(rs.getString("start_date"));
        course.setEndDate(rs.getString("end_date"));
        course.setLocation(rs.getString("location"));
        course.setxCoordinate(rs.getDouble("x_coordinate"));
        course.setyCoordinate(rs.getDouble("y_coordinate"));
        course.setPrice(rs.getInt("price"));
        course.setFree(rs.getBoolean("is_free"));

        setProfessorIndex(course, rs);

        return course;
    }

    private static void setProfessorIndex(Course course, ResultSet rs) throws SQLException {
        User professor = new User();
        professor.setId(rs.getInt("professor_id"));

        course.setProfessor(professor);
    }

    private static void setStudentIndexes(Course course, ResultSet rs) throws SQLException {

    }
}
