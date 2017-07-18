package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.HttpWrapper;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;

import org.apache.log4j.Logger;

/**
 * Controller that load admin page.
 *
 * @author Yaroslav Baranov
 */
public class LoadAdminPageController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoadAdminPageController.class);

    private CourseService courseService;

    public LoadAdminPageController() {
        courseService = ServiceLoader.getInstance().getService(CourseService.class);
    }

    public LoadAdminPageController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Method that get course id from http request and delete it.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        httpWrapper.getRequest().setAttribute(RequestAttribute.COURSES, courseService.getCourses());
        NavigationService.navigateTo(httpWrapper, NavigationConstants.ADMIN_PAGE);
    }
}
