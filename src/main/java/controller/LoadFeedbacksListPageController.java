package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import constants.RequestParameter;
import dispatcher.HttpWrapper;
import entities.Feedback;
import org.apache.log4j.Logger;
import service.*;

import java.util.List;

/**
 * Controller that load page with all feedbacks of course.
 *
 * @author Yaroslav Baranov
 */
public class LoadFeedbacksListPageController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoadFeedbacksListPageController.class);

    private FeedbackService feedbackService;

    public LoadFeedbacksListPageController() {
        feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);
    }

    public LoadFeedbacksListPageController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * Method that get course id from http request and forward to page
     * that contain information about all feedback on this course.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        int id = Integer.parseInt(httpWrapper.getRequest().getParameter(RequestParameter.ID));
        List<Feedback> feedbacks = feedbackService.getFeedbacksByCourseId(id);
        httpWrapper.getRequest().setAttribute(RequestAttribute.FEEDBACKS, feedbacks);
        NavigationService.navigateTo(httpWrapper, NavigationConstants.COURSE_FEEDBACKS_LIST_PAGE);
    }
}
