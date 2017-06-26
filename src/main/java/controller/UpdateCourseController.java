package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Course;
import entities.Location;
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
        Course.Builder builder = Course.newBuilder();

        Location.Builder locationBuilder = Location.newBuilder();
        locationBuilder.setCity(request.getParameter("city"))
                .setAddress(request.getParameter("address"))
                .setXCoordinate(Double.parseDouble(request.getParameter("x")))
                .setYCoordinate(Double.parseDouble(request.getParameter("y")));

        User professor = userService.getUserByLogin(request.getParameter("professor"));


        builder.setName(request.getParameter("name"))
                .setDescription(request.getParameter("description"))
                .setStartDate(request.getParameter("startDate"))
                .setEndDate(request.getParameter("endDate"))
                .setProfessor(professor)
                .setLocation(locationBuilder.build());

        return builder.build();
    }
}
