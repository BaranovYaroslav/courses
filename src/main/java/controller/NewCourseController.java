package controller;

import application.ApplicationConstants;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Course;
import entities.Location;
import entities.User;
import org.apache.log4j.Logger;
import security.UserRoles;
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
public class NewCourseController extends Controller {

    private static Logger LOGGER = Logger.getLogger(LoadAdminPageController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    @Override
    public void get(HttpWrapper httpWrapper) {
        if(validateInputData(httpWrapper)) {
            if(userService.userHasRole(httpWrapper.getRequest().getParameter("professor"), UserRoles.PROFESSOR)) {
                Course course = constructCourse(httpWrapper.getRequest());
                LOGGER.error(course.toString());
                courseService.addNewCourse(course);
                NavigationService.redirectTo(httpWrapper, "/app/admin");
            }
            else {
                httpWrapper.getRequest().setAttribute("message", "There is no professor with such login. Check users list.");
                NavigationService.navigateTo(httpWrapper, "/app/admin/new-course");
            }
        }
        else {

        }
    }

    private boolean validateInputData(HttpWrapper httpWrapper) {
        HttpServletRequest request = httpWrapper.getRequest();

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

        return true;
    }

    private Course constructCourse(HttpServletRequest request) {
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
               .setNumberOfStudents(Integer.parseInt(request.getParameter("students")))
               .setPrice(Double.parseDouble(request.getParameter("price")))
               .setFree(request.getParameter("isFree") != null)
               .setProfessor(professor)
               .setLocation(locationBuilder.build());

        return builder.build();
    }
}
