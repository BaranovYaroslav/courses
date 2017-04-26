package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Feedback;
import org.apache.log4j.Logger;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;
import service.UserService;
import service.impl.CourseServiceImpl;
import service.impl.UserServiceImpl;

import java.util.List;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class LoadFeedbacksListPageController extends Controller {

    private static Logger LOGGER = Logger.getLogger(LoadFeedbacksListPageController.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void get(HttpWrapper reqService) {
        int id = Integer.parseInt(reqService.getRequest().getParameter("id"));
        List<Feedback> feedbacks = courseService.getFeedbacksByCourseId(id);
        reqService.getRequest().setAttribute("feedbacks", feedbacks);
        NavigationService.navigateTo(reqService, "/pages/professor/course-feedbacks.jsp");
    }
}
