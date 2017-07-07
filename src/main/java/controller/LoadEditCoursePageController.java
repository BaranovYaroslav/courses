package controller;

import constants.RequestAttribute;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Course;
import entities.CourseType;
import org.apache.log4j.Logger;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;
import service.impl.CourseServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

public class LoadEditCoursePageController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoginController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        int id = Integer.parseInt(httpWrapper.getRequest().getParameter("id"));
        setAttributesToRequest(httpWrapper, courseService.getCourse(id));
        NavigationService.navigateTo(httpWrapper, "/pages/admin/edit-course.jsp");
    }

    public void setAttributesToRequest(HttpWrapper httpWrapper, Course course) {
        HttpServletRequest request = httpWrapper.getRequest();

        request.setAttribute(RequestAttribute.ID, course.getId());
        request.setAttribute(RequestAttribute.PREVIOUS_NAME, course.getName());
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
