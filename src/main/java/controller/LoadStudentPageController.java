package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.HttpWrapper;
import entities.CourseType;
import service.*;

/**
 * Controller that load student base page.
 *
 * @author Yaroslav Baranov
 */
public class LoadStudentPageController implements Controller {

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    private StudentService studentService = ServiceLoader.getInstance().getService(StudentService.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    /**
     * Method that forward to student base page.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        String login = (String) httpWrapper.getRequest().getSession().getAttribute(RequestAttribute.USER);
        httpWrapper.getRequest().setAttribute(RequestAttribute.COURSES_FOR_REGISTRATION,
                                             studentService.getCoursesForRegistration(userService.getUserByLogin(login)));
        httpWrapper.getRequest().setAttribute(RequestAttribute.TYPES, CourseType.values());
        httpWrapper.getRequest().setAttribute(RequestAttribute.LOCATIONS, courseService.getDistinctCourseLocations());
        httpWrapper.getRequest().setAttribute(RequestAttribute.MAX_COURSE_PRICE, courseService.getMaxPriceOfCourse());
        NavigationService.navigateTo(httpWrapper, NavigationConstants.STUDENT_PAGE);
    }
}
