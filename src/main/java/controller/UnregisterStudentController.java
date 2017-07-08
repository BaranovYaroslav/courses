package controller;

import constants.RequestAttribute;
import constants.RequestParameter;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Course;
import entities.User;
import service.*;
import service.impl.CourseServiceImpl;
import service.impl.UserServiceImpl;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class UnregisterStudentController implements Controller {

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    private FeedbackService feedbackService = ServiceLoader.getInstance().getService(FeedbackService.class);

    @Override
    public void execute(HttpWrapper reqService) {
        String login = (String) reqService.getRequest().getSession().getAttribute(RequestAttribute.USER);
        int courseId = Integer.parseInt(reqService.getRequest().getParameter(RequestParameter.COURSE_ID));

        Course course = courseService.getCourse(courseId);
        User user = userService.getUserByLogin(login);

        courseService.unregisterUser(course, user);
        feedbackService.deleteFeedback(course.getId(), user.getId());
    }
}
