package controller;


import constants.RequestAttribute;
import constants.RequestParameter;
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

public class RegisterStudentController implements Controller{

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        String login = (String) httpWrapper.getRequest().getSession().getAttribute(RequestAttribute.USER);
        int courseId = Integer.parseInt(httpWrapper.getRequest().getParameter(RequestParameter.COURSE_ID));

        Course course = courseService.getCourse(courseId);
        User user = userService.getUserByLogin(login);

        courseService.registerStudent(course, user);

        NavigationService.redirectTo(httpWrapper, "/app/student");
    }
}
