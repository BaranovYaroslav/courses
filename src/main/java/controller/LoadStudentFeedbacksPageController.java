package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.HttpWrapper;
import service.FeedbackService;
import service.NavigationService;
import service.ServiceLoader;

/**
 * Controller that load page with all student feedbacks.
 *
 * @author Yaroslav Baranov
 */
public class LoadStudentFeedbacksPageController implements Controller {

    private FeedbackService feedbackService;

    public LoadStudentFeedbacksPageController() {
        feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);
    }

    public LoadStudentFeedbacksPageController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * Method that get student login from session and forward to page that contains all
     * feedbacks for this student.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        String login = (String) httpWrapper.getRequest().getSession().getAttribute(RequestAttribute.USER);
        httpWrapper.getRequest().setAttribute(RequestAttribute.FEEDBACKS, feedbackService.getFeedbacksForStudent(login));
        NavigationService.navigateTo(httpWrapper, NavigationConstants.STUDENT_FEEDBACKS_PAGE);
    }
}
