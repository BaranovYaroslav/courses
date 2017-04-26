package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Feedback;
import service.FeedbackService;
import service.NavigationService;
import service.ServiceLoader;
import service.impl.FeedbackServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class SaveFeedbackController extends Controller {

    private FeedbackService feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);

    @Override
    public void get(HttpWrapper reqService) {
        int courseId = Integer.parseInt(reqService.getRequest().getParameter("courseId"));
        feedbackService.updateFeedBack(extractFeedback(reqService.getRequest()));
        NavigationService.redirectTo(reqService, "/app/professor/feedbacks?id=" + courseId);
    }

    private Feedback extractFeedback(HttpServletRequest request) {
        Feedback feedback = new Feedback();

        feedback.setId(Integer.parseInt(request.getParameter("id")));
        feedback.setScore(Double.parseDouble(request.getParameter("score")));
        feedback.setComment(request.getParameter("comment"));

        return feedback;
    }
}
