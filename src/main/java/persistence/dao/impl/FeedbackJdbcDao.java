package persistence.dao.impl;

import entities.Feedback;
import entities.User;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.JdbcTemplate;
import persistence.Query;
import persistence.dao.CourseDao;
import persistence.dao.FeedbackDao;
import persistence.dao.UserDao;
import persistence.mappers.FeedbackMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ярослав on 15.04.2017.
 */
public class FeedbackJdbcDao implements FeedbackDao {

    private static Logger LOGGER = Logger.getLogger(UserDao.class);

    private ConnectionManager connectionManager;

    private JdbcTemplate jdbcTemplate;

    private UserDao userDao;

    private CourseDao courseDao;

    public FeedbackJdbcDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        jdbcTemplate = new JdbcTemplate(connectionManager);
        userDao = new UserJdbcDao(connectionManager);
        courseDao = new CourseJdbcDao(connectionManager);
    }

    @Override
    public int add(Feedback feedback) {
        return jdbcTemplate.insert(Query.INSERT_FEEDBACK_QUERY,
                                   feedback.getScore(), feedback.getComment(), feedback.getCourse().getId(),
                                   feedback.getStudent().getId());
    }

    @Override
    public void delete(Feedback feedback) {
        jdbcTemplate.update(Query.DELETE_FEEDBACK_QUERY, feedback.getId());
    }

    @Override
    public int update(Feedback feedback) {
        return jdbcTemplate.update(Query.UPDATE_FEEDBACK_QUERY, feedback.getScore(),
                                   feedback.getComment(), feedback.getId());
    }

    @Override
    public Feedback find(int id) {
        Feedback feedback = jdbcTemplate.queryObject(Query.FIND_FEEDBACK_QUERY, FeedbackMapper::map, id);
        feedback.setCourse(courseDao.find(feedback.getCourse().getId()));
        feedback.setStudent(userDao.find(feedback.getStudent().getId()));
        return feedback;
    }

    @Override
    public List<Feedback> findAll() {
        List<Feedback> feedbacks = jdbcTemplate.queryObjects(Query.FIND_ALL_FEEDBACKS_QUERY, FeedbackMapper::map);
        feedbacks.forEach(feedback -> {
            feedback.setStudent(userDao.find(feedback.getStudent().getId()));
            feedback.setCourse(courseDao.find(feedback.getCourse().getId()));
        });

        return feedbacks;
    }

    @Override
    public List<Feedback> getFeedbacksForCourse(int id) {
        List<Feedback> feedbacks = jdbcTemplate.queryObjects(Query.GET_FEEDBACKS_FOR_COURSE_QUERY,
                                                            FeedbackMapper::map, id);
        feedbacks.forEach(feedback -> {
            feedback.setStudent(userDao.find(feedback.getStudent().getId()));
            feedback.setCourse(courseDao.find(feedback.getCourse().getId()));
        });

        return feedbacks;
    }

    @Override
    public List<Feedback> getFeedBacksForStudentByLogin(String login) {
        User student = userDao.getUser(login);
        List<Feedback> feedbacks = jdbcTemplate.queryObjects(Query.GET_FEEDBACKS_FOR_STUDENT_QUERY,
                                                             FeedbackMapper::map, student.getId());

        feedbacks = feedbacks.stream().filter(this::isGraded).collect(Collectors.toList());
        feedbacks.forEach(feedback -> {
            feedback.setStudent(userDao.find(feedback.getStudent().getId()));
            feedback.setCourse(courseDao.find(feedback.getCourse().getId()));
        });
        return feedbacks;
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
