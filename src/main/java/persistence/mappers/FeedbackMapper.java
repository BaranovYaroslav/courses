package persistence.mappers;

import constants.DatabaseColumn;
import entities.Course;
import entities.CourseType;
import entities.Feedback;
import entities.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that extract feedback from database result set.
 *
 * @author Yaroslav Baranov
 */
public class FeedbackMapper {
    public static Feedback map(ResultSet resultSet) throws SQLException{
        Feedback.Builder builder = Feedback.newBuilder();

        builder.setId(resultSet.getInt(DatabaseColumn.ID))
               .setScore(resultSet.getDouble(DatabaseColumn.SCORE))
               .setComment(resultSet.getString(DatabaseColumn.COMMENT))
               .setStudent(extractStudent(resultSet))
               .setCourse(extractCourse(resultSet));

        return builder.build();
    }

    private static Student extractStudent(ResultSet resultSet) throws SQLException {
        Student.Builder builder = Student.newBuilder();

        builder.setId(resultSet.getInt(DatabaseColumn.STUDENT_ID))
               .setLogin(resultSet.getString(DatabaseColumn.LOGIN))
               .setFullName(resultSet.getString(DatabaseColumn.FULL_NAME))
               .setEmail(resultSet.getString(DatabaseColumn.EMAIL))
               .setPassword(resultSet.getString(DatabaseColumn.PASSWORD));

        return builder.build();
    }

    private static Course extractCourse(ResultSet resultSet) throws SQLException {
        Course.Builder builder = Course.newBuilder();

        builder.setId(resultSet.getInt(DatabaseColumn.COURSE_ID))
                .setName(resultSet.getString(DatabaseColumn.COURSE_NAME))
                .setDescription(resultSet.getString(DatabaseColumn.COURSE_DESCRIPTION))
                .setStartDate(resultSet.getString(DatabaseColumn.START_DATE))
                .setEndDate(resultSet.getString(DatabaseColumn.END_DATE))
                .setNumberOfStudents(resultSet.getInt(DatabaseColumn.STUDENTS_NUMBER))
                .setPrice(resultSet.getInt(DatabaseColumn.PRICE))
                .setFree(resultSet.getBoolean(DatabaseColumn.IS_FREE))
                .setType(CourseType.valueOf(resultSet.getString(DatabaseColumn.TYPE).toUpperCase()));

        return builder.build();
    }
}
