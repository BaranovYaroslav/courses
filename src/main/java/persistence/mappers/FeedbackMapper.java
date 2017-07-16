package persistence.mappers;

import entities.Course;
import entities.Feedback;
import entities.Student;
import entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that extract Feedback from database result set.
 *
 * @author Yaroslav Baranov
 */
public class FeedbackMapper {
    public static Feedback map(ResultSet resultSet) throws SQLException{
        Feedback.Builder builder = Feedback.newBuilder();

        builder.setId(resultSet.getInt("id"))
               .setScore(resultSet.getDouble("score"))
               .setComment(resultSet.getString("comment"))
               .setStudent(initializeStudent(resultSet))
               .setCourse(initializeCourse(resultSet));

        return builder.build();
    }

    private static Student initializeStudent(ResultSet resultSet) throws SQLException {
        Student.Builder builder = Student.newBuilder();
        builder.setId(resultSet.getInt("user_id"));
        return builder.build();
    }

    private static Course initializeCourse(ResultSet resultSet) throws SQLException {
        Course.Builder builder = Course.newBuilder();
        builder.setId(resultSet.getInt("course_id"));
        return builder.build();
    }
}
