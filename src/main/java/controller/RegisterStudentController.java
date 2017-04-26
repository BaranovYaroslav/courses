package controller;


import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Course;
import entities.User;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;
import service.UserService;
import service.impl.CourseServiceImpl;
import service.impl.UserServiceImpl;

public class RegisterStudentController extends Controller{

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    @Override
    public void get(HttpWrapper reqService) {
        String login = (String) reqService.getRequest().getSession().getAttribute("user");
        int courseId = Integer.parseInt(reqService.getRequest().getParameter("courseId"));

        Course course = courseService.getCourse(courseId);
        User user = userService.getUserByLogin(login);

        courseService.registerStudent(course, user);

        NavigationService.navigateTo(reqService, "/app/student");
    }
}
