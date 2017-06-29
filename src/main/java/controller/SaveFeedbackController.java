package controller;

import application.ApplicationConstants;
import application.ValidationConstants;
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
    public void get(HttpWrapper httpWrapper) {
        if(validateInputData(httpWrapper)) {
            int courseId = Integer.parseInt(httpWrapper.getRequest().getParameter("courseId"));
            feedbackService.updateFeedback(extractFeedback(httpWrapper.getRequest()));
            NavigationService.redirectTo(httpWrapper, "/app/professor/feedbacks?id=" + courseId);
        } else {
            returnToPreviousPage(httpWrapper, ApplicationConstants.INCORRECT_INPUT_DATA_MESSAGE);
        }

    }

    private boolean validateInputData(HttpWrapper httpWrapper) {
        HttpServletRequest request = httpWrapper.getRequest();

        String id = request.getParameter("id");
        String score = request.getParameter("score");
        String comment = request.getParameter("comment");

        return id.matches(ValidationConstants.INTEGER_GREATER_THAN_ZERO_REGEX) &&
               score.matches(ValidationConstants.POSITIVE_DOUBLE_REGEX) &&
               comment.matches(ValidationConstants.WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX);
    }

    private Feedback extractFeedback(HttpServletRequest request) {
        Feedback.Builder builder = Feedback.newBuilder();

        builder.setId(Integer.parseInt(request.getParameter("id")))
               .setScore(Double.parseDouble(request.getParameter("score")))
               .setComment(request.getParameter("comment"));

        return builder.build();
    }

    private void returnToPreviousPage(HttpWrapper httpWrapper, String message) {
        String id = httpWrapper.getRequest().getParameter("id");
        httpWrapper.getRequest().setAttribute("message", message);
        NavigationService.navigateTo(httpWrapper, "/app/professor/feedback?id=" + id);
    }
}
