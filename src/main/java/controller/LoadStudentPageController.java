package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.CourseType;
import persistence.dao.UserDao;
import service.*;
import service.impl.StudentServiceImpl;
import service.impl.UserServiceImpl;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class LoadStudentPageController extends Controller {

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    private StudentService studentService = ServiceLoader.getInstance().getService(StudentService.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void get(HttpWrapper httpWrapper) {
        String login = (String) httpWrapper.getRequest().getSession().getAttribute("user");
        httpWrapper.getRequest().setAttribute("coursesForRegistration",
                                             studentService.getCoursesForRegistration(userService.getUserByLogin(login)));
        httpWrapper.getRequest().setAttribute("types", CourseType.values());
        httpWrapper.getRequest().setAttribute("locations", courseService.getDistinctCourseLocations());
        httpWrapper.getRequest().setAttribute("maxPrice", courseService.getMaxPriceOfCourse());
        NavigationService.navigateTo(httpWrapper, "/pages/student/student.jsp");
    }
}
