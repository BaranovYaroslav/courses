package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.FeedbackService;
import service.NavigationService;
import service.ServiceLoader;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class LoadStudentsFeedbacksPageController extends Controller {

    private FeedbackService feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);

    @Override
    public void get(HttpWrapper reqService) {
        String login = (String) reqService.getRequest().getSession().getAttribute("user");
        reqService.getRequest().setAttribute("feedbacks", feedbackService.getFeedbacksForStudent(login));
        NavigationService.navigateTo(reqService, "/pages/student/student-feedbacks.jsp");
    }
}
