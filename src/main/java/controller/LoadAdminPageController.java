package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;

import org.apache.log4j.Logger;

public class LoadAdminPageController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoadAdminPageController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        httpWrapper.getRequest().setAttribute(RequestAttribute.COURSES, courseService.getCourses());
        NavigationService.navigateTo(httpWrapper, NavigationConstants.ADMIN_PAGE);
    }
}
