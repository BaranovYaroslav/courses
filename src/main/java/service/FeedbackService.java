package service;

import entities.Feedback;

import java.util.List;

/**
 * Created by Ярослав on 18.04.2017.
 */
public interface FeedbackService {

    public Feedback getFeedbackById(int id);

    public void updateFeedBack(Feedback feedback);

    public List<Feedback> getFeedbacksByStudentLogin(String login);

}
