package persistence.dao;

import entities.Feedback;

import java.util.List;

/**
 * Created by Ярослав on 15.04.2017.
 */
public interface FeedbackDao extends AbstractDao<Feedback> {

    public List<Feedback> getFeedbacksForCourse(int id);

    public List<Feedback> getFeedBacksForStudentByLogin(String login);

    public void deleteFeedbacksByCourseId(int courseId);

}
