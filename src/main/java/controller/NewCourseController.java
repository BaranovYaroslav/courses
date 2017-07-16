package controller;

import constants.*;
import dispatcher.HttpWrapper;
import entities.*;
import org.apache.log4j.Logger;
import security.UserRole;
import service.*;

import javax.servlet.http.HttpServletRequest;
import java.security.PrivateKey;

/**
 * Controller that provide to admin possibility to create new course.
 *
 * @author Yaroslav Baranov
 */
public class NewCourseController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoadAdminPageController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    private ProfessorService professorService = ServiceLoader.getInstance().getService(ProfessorService.class);

    /**
     * Method that provide processing of new course creation.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        if(validateInputData(httpWrapper)) {
            if(userService.userHasRole(httpWrapper.getRequest().getParameter(RequestParameter.PROFESSOR_LOGIN), UserRole.PROFESSOR)) {
                Course course = constructCourse(httpWrapper.getRequest());
                courseService.addNewCourse(course);
                NavigationService.redirectTo(httpWrapper, NavigationConstants.ADMIN_ROOT_URL);
            }
            else {
                returnToPreviousPage(httpWrapper, Messages.INCORRECT_PROFESSOR_LOGIN_MESSAGE);
            }
        }
        else {
            returnToPreviousPage(httpWrapper, Messages.INCORRECT_INPUT_DATA_MESSAGE);
        }
    }

    /**
     * Method that construct new course from request parameters.
     *
     * @param request http request that contains parameters of new course.
     */
    private Course constructCourse(HttpServletRequest request) {
        Course.Builder builder = Course.newBuilder();

        Location.Builder locationBuilder = Location.newBuilder();
        locationBuilder.setCity(request.getParameter(RequestParameter.CITY))
                .setAddress(request.getParameter(RequestParameter.ADDRESS))
                .setXCoordinate(Double.parseDouble(request.getParameter(RequestParameter.X_COORDINATE)))
                .setYCoordinate(Double.parseDouble(request.getParameter(RequestParameter.Y_COORDINATE)));

        Professor professor = professorService.getProfessorByLogin(request.getParameter(RequestParameter.PROFESSOR_LOGIN));

        builder.setName(request.getParameter(RequestParameter.COURSE_NAME))
               .setDescription(request.getParameter(RequestParameter.DESCRIPTION))
               .setStartDate(request.getParameter(RequestParameter.START_DATE))
               .setEndDate(request.getParameter(RequestParameter.END_DATE))
               .setNumberOfStudents(Integer.parseInt(request.getParameter(RequestParameter.STUDENTS_NUMBER)))
               .setPrice(Double.parseDouble(request.getParameter(RequestParameter.PRICE)))
               .setFree(request.getParameter(RequestParameter.IS_FREE) != null)
               .setProfessor(professor)
               .setLocation(locationBuilder.build())
               .setType(CourseType.valueOf(request.getParameter(RequestParameter.TYPE).toUpperCase()));

        return builder.build();
    }

    /**
     * Method that provide validation of request parameters.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     * @see constants.ValidationConstants
     */
    private boolean validateInputData(HttpWrapper httpWrapper) {
        HttpServletRequest request = httpWrapper.getRequest();

        String name = request.getParameter(RequestParameter.COURSE_NAME);
        String description = request.getParameter(RequestParameter.DESCRIPTION);
        String city = request.getParameter(RequestParameter.CITY);
        String address = request.getParameter(RequestParameter.ADDRESS);
        String x = request.getParameter(RequestParameter.X_COORDINATE);
        String y = request.getParameter(RequestParameter.Y_COORDINATE);
        String numberOfStudents = request.getParameter(RequestParameter.STUDENTS_NUMBER);
        String price = request.getParameter(RequestParameter.PRICE);
        String isFree = request.getParameter(RequestParameter.IS_FREE);
        String startDate = request.getParameter(RequestParameter.START_DATE);
        String endDate = request.getParameter(RequestParameter.END_DATE);
        String professorLogin = request.getParameter(RequestParameter.PROFESSOR_LOGIN);
        String type = request.getParameter(RequestParameter.TYPE);

        return name.matches(ValidationConstants.WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX) &&
               description.matches(ValidationConstants.WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX) &&
               city.matches(ValidationConstants.WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX) &&
               address.matches(ValidationConstants.WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX) &&
               x.matches(ValidationConstants.DOUBLE_REGEX) &&
               y.matches(ValidationConstants.DOUBLE_REGEX) &&
               numberOfStudents.matches(ValidationConstants.INTEGER_GREATER_THAN_ZERO_REGEX) &&
               (isFree == null || isFree.equals(ControllerConstants.CHECKED_VALUE)) &&
               price.matches(ValidationConstants.POSITIVE_DOUBLE_REGEX) &&
               startDate.matches(ValidationConstants.DATE_REGEX) &&
               endDate.matches(ValidationConstants.DATE_REGEX) &&
               professorLogin.matches(ValidationConstants.LOGIN_REGEX) &&
               (type.matches(ValidationConstants.WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX) && (CourseType.valueOf(type.toUpperCase()) != null));
    }

    private void returnToPreviousPage(HttpWrapper httpWrapper, String message) {
        HttpServletRequest request = httpWrapper.getRequest();
        String name = request.getParameter(RequestParameter.COURSE_NAME);
        String description = request.getParameter(RequestParameter.DESCRIPTION);
        String city = request.getParameter(RequestParameter.CITY);
        String address = request.getParameter(RequestParameter.ADDRESS);
        String numberOfStudents = request.getParameter(RequestParameter.STUDENTS_NUMBER);
        String price = request.getParameter(RequestParameter.PRICE);
        String startDate = request.getParameter(RequestParameter.START_DATE);
        String endDate = request.getParameter(RequestParameter.END_DATE);
        String professorLogin = request.getParameter(RequestParameter.PROFESSOR_LOGIN);
        String isFree = request.getParameter(RequestParameter.IS_FREE);
        String x = request.getParameter(RequestParameter.X_COORDINATE);
        String y = request.getParameter(RequestParameter.Y_COORDINATE);
        String type = request.getParameter(RequestParameter.TYPE);

        request.setAttribute(RequestAttribute.PREVIOUS_COURSE_NAME, name);
        request.setAttribute(RequestAttribute.PREVIOUS_DESCRIPTION, description);
        request.setAttribute(RequestAttribute.PREVIOUS_CITY, city);
        request.setAttribute(RequestAttribute.PREVIOUS_ADDRESS, address);
        request.setAttribute(RequestAttribute.PREVIOUS_STUDENTS_NUMBER, numberOfStudents);
        request.setAttribute(RequestAttribute.PREVIOUS_PRICE, price);
        request.setAttribute(RequestAttribute.PREVIOUS_START_DATE, startDate);
        request.setAttribute(RequestAttribute.PREVIOUS_END_DATE, endDate);
        request.setAttribute(RequestAttribute.PREVIOUS_PROFESSOR_LOGIN, professorLogin);
        request.setAttribute(RequestAttribute.PREVIOUS_FREE, isFree == null ? ControllerConstants.FALSE :
                                                                              ControllerConstants.TRUE);
        request.setAttribute(RequestAttribute.PREVIOUS_X_COORDINATE, x);
        request.setAttribute(RequestAttribute.PREVIOUS_Y_COORDINATE, y);
        request.setAttribute(RequestAttribute.PREVIOUS_TYPE, type);
        request.setAttribute(RequestAttribute.MESSAGE, message);

        NavigationService.navigateTo(httpWrapper, NavigationConstants.NEW_COURSE_PAGE);
    }
}
