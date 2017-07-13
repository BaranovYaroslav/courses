package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import constants.RequestParameter;
import dispatcher.HttpWrapper;
import entities.Course;
import entities.CourseType;
import org.apache.log4j.Logger;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller provide to admin possibility to edit course.
 *
 * @author Yaroslav Baranov
 */
public class LoadEditCoursePageController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoginController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    /**
     * Method that set previous course information to request and forward admin to edit page.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        int id = Integer.parseInt(httpWrapper.getRequest().getParameter(RequestParameter.ID));
        setAttributesToRequest(httpWrapper, courseService.getCourse(id));
        NavigationService.navigateTo(httpWrapper, NavigationConstants.EDIT_COURSE_PAGE);
    }


    public void setAttributesToRequest(HttpWrapper httpWrapper, Course course) {
        HttpServletRequest request = httpWrapper.getRequest();

        request.setAttribute(RequestAttribute.ID, course.getId());
        request.setAttribute(RequestAttribute.PREVIOUS_COURSE_NAME, course.getName());
        request.setAttribute(RequestAttribute.PREVIOUS_DESCRIPTION, course.getDescription());
        request.setAttribute(RequestAttribute.PREVIOUS_CITY, course.getLocation().getCity());
        request.setAttribute(RequestAttribute.PREVIOUS_ADDRESS, course.getLocation().getAddress());
        request.setAttribute(RequestAttribute.PREVIOUS_STUDENTS_NUMBER, course.getNumberOfStudents());
        request.setAttribute(RequestAttribute.PREVIOUS_PRICE, course.getPrice());
        request.setAttribute(RequestAttribute.PREVIOUS_START_DATE, course.getStartDate());
        request.setAttribute(RequestAttribute.PREVIOUS_END_DATE, course.getEndDate());
        request.setAttribute(RequestAttribute.PREVIOUS_PROFESSOR_LOGIN, course.getProfessor().getLogin());
        request.setAttribute(RequestAttribute.PREVIOUS_FREE, course.getIsFree());
        request.setAttribute(RequestAttribute.PREVIOUS_X_COORDINATE, course.getLocation().getXCoordinate());
        request.setAttribute(RequestAttribute.PREVIOUS_Y_COORDINATE, course.getLocation().getYCoordinate());
        request.setAttribute(RequestAttribute.PREVIOUS_TYPE, course.getType().getType());
        request.setAttribute(RequestAttribute.TYPES, CourseType.values());
    }
}
