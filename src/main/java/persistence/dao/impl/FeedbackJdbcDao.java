package persistence.dao.impl;

import entities.Feedback;
import entities.Student;
import entities.User;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.JdbcTemplate;
import persistence.Query;
import persistence.dao.CourseDao;
import persistence.dao.FeedbackDao;
import persistence.dao.StudentDao;
import persistence.dao.UserDao;
import persistence.mappers.FeedbackMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Realization of FeedbackDao interface.
 *
 * @see persistence.dao.FeedbackDao
 * @author Yaroslav Baranov
 */
public class FeedbackJdbcDao implements FeedbackDao {

    private JdbcTemplate jdbcTemplate;


    public FeedbackJdbcDao(ConnectionManager connectionManager) {
        jdbcTemplate = new JdbcTemplate(connectionManager);
    }

    @Override
    public Integer add(Feedback feedback) {
        return jdbcTemplate.insert(Query.INSERT_FEEDBACK_QUERY,
                                   feedback.getScore(), feedback.getComment(), feedback.getCourse().getId(),
                                   feedback.getStudent().getId());
    }

    @Override
    public void delete(Feedback feedback) {
        jdbcTemplate.update(Query.DELETE_FEEDBACK_QUERY, feedback.getId());
    }

    @Override
    public Integer update(Feedback feedback) {
        return jdbcTemplate.update(Query.UPDATE_FEEDBACK_QUERY, feedback.getScore(),
                                   feedback.getComment(), feedback.getId());
    }

    @Override
    public Optional<Feedback> find(Integer id) {
        return jdbcTemplate.queryObject(Query.FIND_FEEDBACK_QUERY, FeedbackMapper::map, id);
    }

    @Override
    public List<Feedback> findAll() {
        return jdbcTemplate.queryObjects(Query.FIND_ALL_FEEDBACKS_QUERY, FeedbackMapper::map);
    }

    @Override
    public List<Feedback> getFeedbacksForCourse(int id) {
        return jdbcTemplate.queryObjects(Query.GET_FEEDBACKS_FOR_COURSE_QUERY, FeedbackMapper::map, id);
    }

    @Override
    public List<Feedback> getFeedBacksForStudentByLogin(String login) {
        List<Feedback> feedbacks =jdbcTemplate.queryObjects(Query.GET_FEEDBACKS_FOR_STUDENT_QUERY,
                                                            FeedbackMapper::map, login);
        return feedbacks.stream().filter(this::isGraded).collect(Collectors.toList());
    }

    @Override
    public void deleteFeedbacksByCourseId(int courseId) {
        jdbcTemplate.update(Query.DELETE_FEEDBACKS_BY_COURSE_QUERY, courseId);
    }

    @Override
    public void deleteByCourseAndStudentId(int courseId, int studentId) {
        jdbcTemplate.update(Query.DELETE_FEEDBACK_BY_COURSE_AND_STUDENT_ID_QUERY, courseId, studentId);
    }

    private boolean isGraded(Feedback feedback){
        return ((feedback.getScore() != 0) && (feedback.getComment().length() > 0));
    }
}
