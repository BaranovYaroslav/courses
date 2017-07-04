package controller;

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

        request.setAttribute("id", course.getId());
        request.setAttribute("previousName", course.getName());
        request.setAttribute("previousDescription", course.getDescription());
        request.setAttribute("previousCity", course.getLocation().getCity());
        request.setAttribute("previousAddress", course.getLocation().getAddress());
        request.setAttribute("previousNumberOfStudents", course.getNumberOfStudents());
        request.setAttribute("previousPrice", course.getPrice());
        request.setAttribute("previousStartDate", course.getStartDate());
        request.setAttribute("previousEndDate", course.getEndDate());
        request.setAttribute("previousProfessorLogin", course.getProfessor().getLogin());
        request.setAttribute("previousFree", course.getIsFree());
        request.setAttribute("previousX", course.getLocation().getXCoordinate());
        request.setAttribute("previousY", course.getLocation().getYCoordinate());
        request.setAttribute("previousType", course.getType().getType());
        request.setAttribute("types", CourseType.values());
    }
}
