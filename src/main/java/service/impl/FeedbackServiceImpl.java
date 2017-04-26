package service.impl;

import entities.Feedback;
import persistence.ConnectionManager;
import persistence.dao.FeedbackDao;
import persistence.dao.factory.DaoFactory;
import persistence.dao.impl.FeedbackJdbcDao;
import service.FeedbackService;

import java.util.List;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackDao feedbackDao;

    public FeedbackServiceImpl(DaoFactory daoFactory) {
        feedbackDao = daoFactory.getFeedbackDao();
    }

    @Override
    public Feedback getFeedbackById(int id) {
        return feedbackDao.find(id);
    }

    @Override
    public void updateFeedBack(Feedback feedback) {
        feedbackDao.update(feedback);
    }

    @Override
    public List<Feedback> getFeedbacksByStudentLogin(String login) {
        return feedbackDao.getFeedBacksForStudentByLogin(login);
    }
}
