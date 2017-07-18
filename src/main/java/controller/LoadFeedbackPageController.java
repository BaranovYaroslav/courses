package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import constants.RequestParameter;
import dispatcher.HttpWrapper;
import service.FeedbackService;
import service.NavigationService;
import service.ServiceLoader;

/**
 * Controller that load page with feedback details.
 *
 * @author Yaroslav Baranov
 */
public class LoadFeedbackPageController implements Controller {

    private FeedbackService feedbackService;

    public LoadFeedbackPageController() {
        feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);
    }

    public LoadFeedbackPageController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * Method that get feedback id from http request forward to
     * page that contains information about this feedback.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        int id = Integer.parseInt(httpWrapper.getRequest().getParameter(RequestParameter.ID));
        httpWrapper.getRequest().setAttribute(RequestAttribute.FEEDBACK, feedbackService.getFeedback(id));
        NavigationService.navigateTo(httpWrapper, NavigationConstants.EDIT_FEEDBACK_PAGE);
    }
}
