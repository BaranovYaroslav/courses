package service;

import entities.Feedback;

import java.util.List;

/**
 * Created by Ярослав on 18.04.2017.
 */
public interface FeedbackService {

    public Feedback getFeedback(int id);

    public void updateFeedback(Feedback feedback);

    public List<Feedback> getFeedbacksForStudent(String login);
}
