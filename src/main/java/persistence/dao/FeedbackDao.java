package persistence.dao;

import entities.Feedback;

import java.util.List;

/**
 * Interface for feedback data access object.
 *
 * @author Yaroslav Baranov
 */
public interface FeedbackDao extends AbstractDao<Feedback, Integer> {

    /**
     * Method that extract all feedbacks for given course.
     *
     * @param courseId id of course
     * @return list of feedbacks
     */
    public List<Feedback> getFeedbacksForCourse(int courseId);

    /**
     * Method that extract all feedbacks for student.
     *
     * @param login login of student
     * @return list of feedbacks
     */
    public List<Feedback> getFeedBacksForStudentByLogin(String login);

    /**
     * Method that delete all feedback of given course.
     *
     * @param courseId id of course
     */
    public void deleteFeedbacksByCourseId(int courseId);

    /**
     * Method that delete feedback of given course and student.
     *
     * @param courseId id of course
     * @param studentId id of student
     */
    public void deleteByCourseAndStudentId(int courseId, int studentId);
}
