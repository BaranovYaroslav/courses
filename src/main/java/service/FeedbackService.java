package service;

import entities.Course;
import entities.Feedback;
import entities.User;

import java.util.List;

/**
 * Created by Ярослав on 18.04.2017.
 */
public interface FeedbackService {

    public Feedback getFeedback(int id);

    public void updateFeedback(Feedback feedback);

    public List<Feedback> getFeedbacksForStudent(String login);

    public void deleteFeedback(int courseId, int studentId);

    public List<Feedback> getFeedbacksByCourseId(int id);

    public static Feedback createEmptyFeedback(Course course, User user) {
        Feedback.Builder builder = Feedback.newBuilder();

        builder.setComment("")
                .setScore(0)
                .setStudent(user)
                .setCourse(course);

        return builder.build();
    }
}
