package persistence.mappers;

import entities.Course;
import entities.Feedback;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class FeedbackMapper {
    public static Feedback map(ResultSet resultSet) throws SQLException{
        Feedback feedback = new Feedback();

        feedback.setId(resultSet.getInt("id"));
        feedback.setScore(resultSet.getDouble("score"));
        feedback.setComment(resultSet.getString("comment"));
        feedback.setStudent(initializeUser(resultSet));
        feedback.setCourse(initializeCourse(resultSet));

        return feedback;
    }

    private static User initializeUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        return user;
    }

    private static Course initializeCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt("course_id"));
        return course;
    }
}
