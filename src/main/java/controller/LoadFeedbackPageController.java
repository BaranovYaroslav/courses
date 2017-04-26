package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Feedback;
import service.CourseService;
import service.FeedbackService;
import service.NavigationService;
import service.ServiceLoader;
import service.impl.FeedbackServiceImpl;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class LoadFeedbackPageController extends Controller {

    private FeedbackService feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);

    @Override
    public void get(HttpWrapper reqService) {
        int id = Integer.parseInt(reqService.getRequest().getParameter("id"));
        reqService.getRequest().setAttribute("feedback", feedbackService.getFeedbackById(id));
        NavigationService.navigateTo(reqService, "/pages/professor/feedback.jsp");
    }
}
