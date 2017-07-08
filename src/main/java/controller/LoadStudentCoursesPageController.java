package controller;

import constants.RequestAttribute;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;
import service.impl.CourseServiceImpl;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class LoadStudentCoursesPageController implements Controller {

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void execute(HttpWrapper reqService) {
        String login = (String) reqService.getRequest().getSession().getAttribute(RequestAttribute.USER);
        reqService.getRequest().setAttribute(RequestAttribute.COURSES, courseService.getCoursesForStudent(login));
        NavigationService.navigateTo(reqService, "/pages/student/student-courses.jsp");
    }
}
