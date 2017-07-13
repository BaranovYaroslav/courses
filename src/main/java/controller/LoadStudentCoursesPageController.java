package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;
import service.StudentService;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class LoadStudentCoursesPageController implements Controller {

    private StudentService studentService = ServiceLoader.getInstance().getService(StudentService.class);

    @Override
    public void execute(HttpWrapper reqService) {
        String login = (String) reqService.getRequest().getSession().getAttribute(RequestAttribute.USER);
        reqService.getRequest().setAttribute(RequestAttribute.COURSES, studentService.getCoursesForStudent(login));
        NavigationService.navigateTo(reqService, NavigationConstants.STUDENT_COURSES_PAGE);
    }
}
