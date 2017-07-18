package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import constants.RequestParameter;
import dispatcher.HttpWrapper;
import persistence.dao.CourseDao;
import persistence.dao.factory.DaoFactory;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;

/**
 * Controller that provide to user details information about course.
 *
 * @author Yaroslav Baranov
 */
public class CourseDetailsController implements Controller {

    private CourseService courseService;

    public CourseDetailsController() {
        courseService = ServiceLoader.getInstance().getService(CourseService.class);
    }

    public CourseDetailsController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Method that get course id from http request and forward to page
     * that contain information about this course.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        Integer courseId = Integer.parseInt(httpWrapper.getRequest().getParameter(RequestParameter.COURSE_ID));
        httpWrapper.getRequest().setAttribute(RequestAttribute.COURSE, courseService.getCourse(courseId));
        NavigationService.navigateTo(httpWrapper, NavigationConstants.COURSE_DETAILS_PAGE);
    }
}
