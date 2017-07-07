package controller;

import constants.ApplicationConstants;
import constants.ControllerConstants;
import constants.RequestAttribute;
import constants.ValidationConstants;
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
            if(userService.userHasRole(httpWrapper.getRequest().getParameter("professor"), UserRole.PROFESSOR)) {
                Course course = constructCourse(httpWrapper.getRequest());
                courseService.updateCourse(course);
                NavigationService.redirectTo(httpWrapper, "/app/admin");
            }
            else {
                returnToPreviousPage(httpWrapper, "There is no professor with such login. Check users list.");
            }
        } else {
            returnToPreviousPage(httpWrapper, ApplicationConstants.INCORRECT_INPUT_DATA_MESSAGE);
        }
    }

    private Course constructCourse(HttpServletRequest request){
        Course.Builder builder = Course.newBuilder();

        Location.Builder locationBuilder = Location.newBuilder();
        locationBuilder.setCity(request.getParameter("city"))
                .setAddress(request.getParameter("address"))
                .setXCoordinate(Double.parseDouble(request.getParameter("x")))
                .setYCoordinate(Double.parseDouble(request.getParameter("y")));

        User professor = userService.getUserByLogin(request.getParameter("professor"));

        builder.setId(Integer.parseInt(request.getParameter("id")))
               .setName(request.getParameter("name"))
               .setDescription(request.getParameter("description"))
               .setStartDate(request.getParameter("startDate"))
               .setEndDate(request.getParameter("endDate"))
               .setNumberOfStudents(Integer.parseInt(request.getParameter("students")))
               .setPrice(Double.parseDouble(request.getParameter("price")))
               .setFree(request.getParameter("isFree") != null)
               .setProfessor(professor)
               .setLocation(locationBuilder.build())
               .setType(CourseType.valueOf(request.getParameter("type").toUpperCase()));;

        return builder.build();
    }

    private boolean validateInputData(HttpWrapper httpWrapper) {
        HttpServletRequest request = httpWrapper.getRequest();

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String numberOfStudents = request.getParameter("students");
        String price = request.getParameter("price");
        String isFree = request.getParameter("isFree");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String professorLogin = request.getParameter("professor");
        String type = request.getParameter("type");

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
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String numberOfStudents = request.getParameter("students");
        String price = request.getParameter("price");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String professorLogin = request.getParameter("professor");
        String isFree = request.getParameter("isFree") == null ? "false" : "true" ;
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String type = request.getParameter("type");

        request.setAttribute(RequestAttribute.ID, id);
        request.setAttribute(RequestAttribute.PREVIOUS_NAME, name);
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

        NavigationService.navigateTo(httpWrapper, "/pages/admin/edit-course.jsp");
    }
}
