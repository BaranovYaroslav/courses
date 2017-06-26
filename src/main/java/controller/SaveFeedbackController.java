package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Feedback;
import service.FeedbackService;
import service.NavigationService;
import service.ServiceLoader;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class SaveFeedbackController extends Controller {

    private FeedbackService feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);

    @Override
    public void get(HttpWrapper reqService) {
        int courseId = Integer.parseInt(reqService.getRequest().getParameter("courseId"));
        feedbackService.updateFeedback(extractFeedback(reqService.getRequest()));
        NavigationService.redirectTo(reqService, "/app/professor/feedbacks?id=" + courseId);
    }

    private Feedback extractFeedback(HttpServletRequest request) {
        Feedback.Builder builder = Feedback.newBuilder();

        builder.setId(Integer.parseInt(request.getParameter("id")))
               .setScore(Double.parseDouble(request.getParameter("score")))
               .setComment(request.getParameter("comment"));

        return builder.build();
    }
}
