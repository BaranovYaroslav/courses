package controller;

import constants.RequestAttribute;
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
public class LoadStudentPageController implements Controller {

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    private StudentService studentService = ServiceLoader.getInstance().getService(StudentService.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        String login = (String) httpWrapper.getRequest().getSession().getAttribute(RequestAttribute.USER);
        httpWrapper.getRequest().setAttribute(RequestAttribute.MAX_COURSE_PRICE,
                                             studentService.getCoursesForRegistration(userService.getUserByLogin(login)));
        httpWrapper.getRequest().setAttribute(RequestAttribute.TYPES, CourseType.values());
        httpWrapper.getRequest().setAttribute(RequestAttribute.LOCATIONS, courseService.getDistinctCourseLocations());
        httpWrapper.getRequest().setAttribute(RequestAttribute.MAX_COURSE_PRICE, courseService.getMaxPriceOfCourse());
        NavigationService.navigateTo(httpWrapper, "/pages/student/student.jsp");
    }
}
