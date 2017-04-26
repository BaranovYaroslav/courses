package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Course;
import entities.User;
import org.apache.log4j.Logger;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;
import service.UserService;
import service.impl.CourseServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ярослав on 16.04.2017.
 */
public class UpdateCourseController extends Controller {

    private static Logger LOGGER = Logger.getLogger(UpdateCourseController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    @Override
    public void get(HttpWrapper reqService) {
        Course course = constructCourse(reqService.getRequest());
        courseService.updateCourse(course);
        NavigationService.redirectTo(reqService, "/app/admin");
    }

    private Course constructCourse(HttpServletRequest request){
        Course course = new Course();

        course.setId(Integer.parseInt(request.getParameter("id")));
        course.setName(request.getParameter("name"));
        course.setDescription(request.getParameter("description"));
        course.setLocation(request.getParameter("location"));
        course.setStartDate(request.getParameter("startDate"));
        course.setEndDate(request.getParameter("endDate"));
        course.setxCoordinate(Double.parseDouble(request.getParameter("xCoordinate")));
        course.setyCoordinate(Double.parseDouble(request.getParameter("yCoordinate")));

        User professor = userService.getUserByLogin(request.getParameter("professor"));
        LOGGER.error("pro: " + professor);
        course.setProfessor(professor);

        return course;
    }
}
