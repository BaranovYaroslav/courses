package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.FeedbackService;
import service.NavigationService;
import service.ServiceLoader;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class LoadFeedbackPageController implements Controller {

    private FeedbackService feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);

    @Override
    public void execute(HttpWrapper reqService) {
        int id = Integer.parseInt(reqService.getRequest().getParameter("id"));
        reqService.getRequest().setAttribute("feedback", feedbackService.getFeedback(id));
        NavigationService.navigateTo(reqService, "/pages/professor/feedback.jsp");
    }
}
