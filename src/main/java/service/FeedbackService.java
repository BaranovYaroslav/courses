package service;

import entities.Course;
import entities.Feedback;
import entities.Student;
import entities.User;

import java.util.List;

/**
 * Interface of service to provide business options with feedback.
 *
 * @author Yaroslav Baranov
 */
public interface FeedbackService {

    /**
     * Method to get feedback by id.
     *
     * @param id id of feedback
     * @return found feedback
     */
    public Feedback getFeedback(int id);

    /**
     * Method to update feedback in database.
     *
     * @param feedback feedback to be updated
     */
    public void updateFeedback(Feedback feedback);

    /**
     * Method to get all feedback for student.
     *
     * @param login login of student
     * @return list of feedbacks
     */
    public List<Feedback> getFeedbacksForStudent(String login);

    /**
     * Method to delete feedback by course id and student id.
     *
     * @param courseId id of course
     * @param studentId id of student
     */
    public void deleteFeedback(int courseId, int studentId);

    /**
     * Method to get all feedback for course.
     *
     * @param courseId id of course
     * @return list of feedbacks
     */
    public List<Feedback> getFeedbacksByCourseId(int courseId);

    /**
     * Method to create empty feedback.
     *
     * @param course course of feedback
     * @param student student of feedback
     *
     * @return created feedback
     */
    public static Feedback createEmptyFeedback(Course course, Student student) {
        Feedback.Builder builder = Feedback.newBuilder();

        builder.setComment("")
                .setScore(0)
                .setStudent(student)
                .setCourse(course);

        return builder.build();
    }
}
