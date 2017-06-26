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
        Feedback.Builder builder = Feedback.newBuilder();

        builder.setId(resultSet.getInt("id"))
               .setScore(resultSet.getDouble("score"))
               .setComment(resultSet.getString("comment"))
               .setStudent(initializeUser(resultSet))
               .setCourse(initializeCourse(resultSet));

        return builder.build();
    }

    private static User initializeUser(ResultSet resultSet) throws SQLException {
        User.Builder builder = User.newBuilder();
        builder.setId(resultSet.getInt("user_id"));
        return builder.build();
    }

    private static Course initializeCourse(ResultSet resultSet) throws SQLException {
        Course.Builder builder = Course.newBuilder();
        builder.setId(resultSet.getInt("course_id"));
        return builder.build();
    }
}
