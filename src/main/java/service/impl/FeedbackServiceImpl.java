package service.impl;

import entities.Feedback;
import persistence.ConnectionManager;
import persistence.dao.FeedbackDao;
import persistence.dao.factory.DaoFactory;
import persistence.dao.impl.FeedbackJdbcDao;
import service.FeedbackService;

import java.util.List;

/**
 * Realization of FeedbackService interface.
 *
 * @see service.FeedbackService
 * @author Yaroslav Baranov
 */
public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackDao feedbackDao;

    public FeedbackServiceImpl(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }

    public FeedbackServiceImpl(DaoFactory daoFactory) {
        feedbackDao = daoFactory.getFeedbackDao();
    }

    @Override
    public Feedback getFeedback(int id) {
        return feedbackDao.find(id).get();
    }

    @Override
    public void updateFeedback(Feedback feedback) {
        feedbackDao.update(feedback);
    }

    @Override
    public List<Feedback> getFeedbacksForStudent(String login) {
        return feedbackDao.getFeedBacksForStudentByLogin(login);
    }

    @Override
    public void deleteFeedback(int courseId, int studentId) {
        feedbackDao.deleteByCourseAndStudentId(courseId, studentId);
    }

    @Override
    public List<Feedback> getFeedbacksByCourseId(int id) {
        return feedbackDao.getFeedbacksForCourse(id);
    }
}
