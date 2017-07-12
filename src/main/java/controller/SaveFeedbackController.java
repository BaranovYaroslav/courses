package controller;

import constants.*;
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
public class SaveFeedbackController implements Controller {

    private FeedbackService feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        if(validateInputData(httpWrapper)) {
            int courseId = Integer.parseInt(httpWrapper.getRequest().getParameter(RequestParameter.COURSE_ID));
            feedbackService.updateFeedback(extractFeedback(httpWrapper.getRequest()));
            NavigationService.redirectTo(httpWrapper, NavigationConstants.FEEDBACKS_FOR_COURSE_URL + courseId);
        } else {
            returnToPreviousPage(httpWrapper, Messages.INCORRECT_INPUT_DATA_MESSAGE);
        }

    }

    private boolean validateInputData(HttpWrapper httpWrapper) {
        HttpServletRequest request = httpWrapper.getRequest();

        String id = request.getParameter(RequestParameter.ID);
        String score = request.getParameter(RequestParameter.SCORE);
        String comment = request.getParameter(RequestParameter.COMMENT);

        return id.matches(ValidationConstants.INTEGER_GREATER_THAN_ZERO_REGEX) &&
               score.matches(ValidationConstants.POSITIVE_DOUBLE_REGEX) &&
               comment.matches(ValidationConstants.WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX);
    }

    private Feedback extractFeedback(HttpServletRequest request) {
        Feedback.Builder builder = Feedback.newBuilder();

        builder.setId(Integer.parseInt(request.getParameter(RequestParameter.ID)))
               .setScore(Double.parseDouble(request.getParameter(RequestParameter.SCORE)))
               .setComment(request.getParameter(RequestParameter.COMMENT));

        return builder.build();
    }

    private void returnToPreviousPage(HttpWrapper httpWrapper, String message) {
        String id = httpWrapper.getRequest().getParameter(RequestParameter.ID);
        httpWrapper.getRequest().setAttribute(RequestAttribute.MESSAGE, message);
        NavigationService.navigateTo(httpWrapper, NavigationConstants.FEEDBACK_URL + id);
    }
}
