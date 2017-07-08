package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import constants.RequestParameter;
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
public class LoadFeedbacksListPageController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoadFeedbacksListPageController.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void execute(HttpWrapper reqService) {
        int id = Integer.parseInt(reqService.getRequest().getParameter(RequestParameter.ID));
        List<Feedback> feedbacks = courseService.getFeedbacksByCourseId(id);
        reqService.getRequest().setAttribute(RequestAttribute.FEEDBACKS, feedbacks);
        NavigationService.navigateTo(reqService, NavigationConstants.COURSE_FEEDBACKS_LIST_PAGE);
    }
}
