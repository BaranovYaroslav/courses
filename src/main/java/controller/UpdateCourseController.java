package controller;

import constants.*;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Course;
import entities.CourseType;
import entities.Location;
import entities.User;
import org.apache.log4j.Logger;
import security.UserRole;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;
import service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ярослав on 16.04.2017.
 */
public class UpdateCourseController implements Controller {

    private static Logger LOGGER = Logger.getLogger(UpdateCourseController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        if(validateInputData(httpWrapper)) {
            if(userService.userHasRole(httpWrapper.getRequest().getParameter(RequestParameter.PROFESSOR_LOGIN), UserRole.PROFESSOR)) {
                Course course = constructCourse(httpWrapper.getRequest());
                courseService.updateCourse(course);
                NavigationService.redirectTo(httpWrapper, NavigationConstants.ADMIN_ROOT_URL);
            }
            else {
                returnToPreviousPage(httpWrapper, Messages.INCORRECT_PROFESSOR_LOGIN_MESSAGE);
            }
        } else {
            returnToPreviousPage(httpWrapper, Messages.INCORRECT_INPUT_DATA_MESSAGE);
        }
    }

    private Course constructCourse(HttpServletRequest request){
        Course.Builder builder = Course.newBuilder();

        Location.Builder locationBuilder = Location.newBuilder();
        locationBuilder.setCity(request.getParameter(RequestParameter.CITY))
                .setAddress(request.getParameter(RequestParameter.ADDRESS))
                .setXCoordinate(Double.parseDouble(request.getParameter(RequestParameter.X_COORDINATE)))
                .setYCoordinate(Double.parseDouble(request.getParameter(RequestParameter.Y_COORDINATE)));

        User professor = userService.getUserByLogin(request.getParameter(RequestParameter.PROFESSOR_LOGIN));

        builder.setId(Integer.parseInt(request.getParameter(RequestParameter.ID)))
               .setName(request.getParameter(RequestParameter.COURSE_NAME))
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

    private boolean validateInputData(HttpWrapper httpWrapper) {
        HttpServletRequest request = httpWrapper.getRequest();

        String id = request.getParameter(RequestParameter.ID);
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

        return id.matches(ValidationConstants.INTEGER_GREATER_THAN_ZERO_REGEX) &&
               name.matches(ValidationConstants.WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX) &&
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
        String id = request.getParameter(RequestParameter.ID);
        String name = request.getParameter(RequestParameter.COURSE_NAME);
        String description = request.getParameter(RequestParameter.DESCRIPTION);
        String city = request.getParameter(RequestParameter.CITY);
        String address = request.getParameter(RequestParameter.ADDRESS);
        String numberOfStudents = request.getParameter(RequestParameter.STUDENTS_NUMBER);
        String price = request.getParameter(RequestParameter.PRICE);
        String startDate = request.getParameter(RequestParameter.START_DATE);
        String endDate = request.getParameter(RequestParameter.END_DATE);
        String professorLogin = request.getParameter(RequestParameter.PROFESSOR_LOGIN);
        String isFree = request.getParameter(RequestParameter.IS_FREE) == null ? "false" : "true" ;
        String x = request.getParameter(RequestParameter.X_COORDINATE);
        String y = request.getParameter(RequestParameter.Y_COORDINATE);
        String type = request.getParameter(RequestParameter.TYPE);

        request.setAttribute(RequestAttribute.ID, id);
        request.setAttribute(RequestAttribute.PREVIOUS_COURSE_NAME, name);
        request.setAttribute(RequestAttribute.PREVIOUS_DESCRIPTION, description);
        request.setAttribute(RequestAttribute.PREVIOUS_CITY, city);
        request.setAttribute(RequestAttribute.PREVIOUS_ADDRESS, address);
        request.setAttribute(RequestAttribute.PREVIOUS_STUDENTS_NUMBER, numberOfStudents);
        request.setAttribute(RequestAttribute.PREVIOUS_PRICE, price);
        request.setAttribute(RequestAttribute.PREVIOUS_START_DATE, startDate);
        request.setAttribute(RequestAttribute.PREVIOUS_END_DATE, endDate);
        request.setAttribute(RequestAttribute.PREVIOUS_PROFESSOR_LOGIN, professorLogin);
        request.setAttribute(RequestAttribute.PREVIOUS_FREE, isFree);
        request.setAttribute(RequestAttribute.PREVIOUS_X_COORDINATE, x);
        request.setAttribute(RequestAttribute.PREVIOUS_Y_COORDINATE, y);
        request.setAttribute(RequestAttribute.PREVIOUS_TYPE, type);
        request.setAttribute(RequestAttribute.TYPES, CourseType.values());
        request.setAttribute(RequestAttribute.MESSAGE, message);

        NavigationService.navigateTo(httpWrapper, NavigationConstants.EDIT_COURSE_PAGE);
    }
}
