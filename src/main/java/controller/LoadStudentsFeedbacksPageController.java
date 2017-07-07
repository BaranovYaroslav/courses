package controller;

import constants.RequestAttribute;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.FeedbackService;
import service.NavigationService;
import service.ServiceLoader;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class LoadStudentsFeedbacksPageController implements Controller {

    private FeedbackService feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);

    @Override
    public void execute(HttpWrapper reqService) {
        String login = (String) reqService.getRequest().getSession().getAttribute(RequestAttribute.USER);
        reqService.getRequest().setAttribute(RequestAttribute.FEEDBACKS, feedbackService.getFeedbacksForStudent(login));
        NavigationService.navigateTo(reqService, "/pages/student/student-feedbacks.jsp");
    }
}
