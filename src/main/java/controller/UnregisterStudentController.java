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

/**
 * Created by Ярослав on 18.04.2017.
 */
public class UnregisterStudentController implements Controller {

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    @Override
    public void execute(HttpWrapper reqService) {
        String login = (String) reqService.getRequest().getSession().getAttribute("user");
        int courseId = Integer.parseInt(reqService.getRequest().getParameter("courseId"));

        Course course = courseService.getCourse(courseId);
        User user = userService.getUserByLogin(login);

        courseService.unregisterUser(course, user);
    }
}
