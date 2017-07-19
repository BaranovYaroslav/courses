package controller;

import constants.*;
import dispatcher.HttpWrapper;
import entities.Feedback;
import service.FeedbackService;
import service.NavigationService;
import service.ServiceLoader;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller provide to professor possibility to edit feedback.
 *
 * @author Yaroslav Baranov
 */
public class SaveFeedbackController implements Controller {

    private FeedbackService feedbackService;

    public SaveFeedbackController() {
        feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);
    }

    public SaveFeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * Method that process feedback editing.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        if(validateInputData(httpWrapper)) {
            int courseId = Integer.parseInt(httpWrapper.getRequest().getParameter(RequestParameter.COURSE_ID));
            feedbackService.updateFeedback(constructFeedback(httpWrapper.getRequest()));
            NavigationService.redirectTo(httpWrapper, NavigationConstants.FEEDBACKS_FOR_COURSE_URL + courseId);
        } else {
            returnToPreviousPage(httpWrapper, Messages.INCORRECT_INPUT_DATA_MESSAGE);
        }

    }

    /**
     * Method that provide validation of request parameters.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     * @see constants.ValidationConstants
     */
    private boolean validateInputData(HttpWrapper httpWrapper) {
        HttpServletRequest request = httpWrapper.getRequest();

        String id = request.getParameter(RequestParameter.ID);
        String score = request.getParameter(RequestParameter.SCORE);
        String comment = request.getParameter(RequestParameter.COMMENT);

        return id.matches(ValidationConstants.INTEGER_GREATER_THAN_ZERO_REGEX) &&
               score.matches(ValidationConstants.POSITIVE_DOUBLE_REGEX) &&
               comment.matches(ValidationConstants.UTF8_TRIMMED_STRING_REGEX);
    }

    /**
     * Method that construct feedback from request parameters.
     *
     * @param request http request that contains parameters of new course.
     */
    private Feedback constructFeedback(HttpServletRequest request) {
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
