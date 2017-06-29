package persistence.dao.impl;

import entities.Feedback;
import entities.User;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.JdbcTemplate;
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
        return jdbcTemplate.insert("INSERT INTO `feedback` (`score`, `comment`, `course_id`, `user_id`) VALUES(?, ?, ?, ?);",
                                   feedback.getScore(), feedback.getComment(), feedback.getCourse().getId(),
                                   feedback.getStudent().getId());
    }

    @Override
    public void delete(Feedback feedback) {
        jdbcTemplate.update("DELETE FROM `feedback` where id=?;", feedback.getId());
    }

    @Override
    public int update(Feedback feedback) {
        return jdbcTemplate.update("UPDATE `feedback` SET `score`=?, `comment`=? WHERE id=?;", feedback.getScore(),
                                   feedback.getComment(), feedback.getId());
    }

    @Override
    public Feedback find(int id) {
        Feedback feedback = jdbcTemplate.queryObject("SELECT * FROM `feedback` WHERE `id`=?",
                                                     FeedbackMapper::map, id);
        feedback.setCourse(courseDao.find(feedback.getCourse().getId()));
        feedback.setStudent(userDao.find(feedback.getStudent().getId()));
        return feedback;
    }

    @Override
    public List<Feedback> findAll() {
        List<Feedback> feedbacks = jdbcTemplate.queryObjects("SELECT * FROM `feedback` WHERE `course_id`=?",
                                                             FeedbackMapper::map);
        feedbacks.forEach(feedback -> {
            feedback.setStudent(userDao.find(feedback.getStudent().getId()));
            feedback.setCourse(courseDao.find(feedback.getCourse().getId()));
        });

        return feedbacks;
    }

    @Override
    public List<Feedback> getFeedbacksForCourse(int id) {
        List<Feedback> feedbacks = jdbcTemplate.queryObjects("SELECT * FROM `feedback` WHERE `course_id`=?",
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
        List<Feedback> feedbacks = jdbcTemplate.queryObjects("SELECT * FROM `feedback` WHERE `user_id`=?;",
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
        jdbcTemplate.update("DELETE FROM `feedback` WHERE `course_id`=?;", courseId);
    }

    private boolean isGraded(Feedback feedback){
        return ((feedback.getScore() != 0) && (feedback.getComment().length() > 0));
    }
}
