package controller;

import constants.NavigationConstants;
import constants.RequestParameter;
import dispatcher.HttpWrapper;
import org.apache.log4j.Logger;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;

/**
 * Controller that provide possibility to delete course for admin.
 *
 * @author Yaroslav Baranov
 */
public class DeleteCourseController implements Controller {

    private static Logger LOGGER = Logger.getLogger(DeleteCourseController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    /**
     * Method that get course id from http request and delete it.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        Integer id = Integer.parseInt(httpWrapper.getRequest().getParameter(RequestParameter.ID));
        courseService.deleteCourse(id);
        NavigationService.redirectTo(httpWrapper, NavigationConstants.ADMIN_ROOT_URL);
    }
}
