package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;
import service.impl.CourseServiceImpl;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class LoadStudentCoursesPageController extends Controller {

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void get(HttpWrapper reqService) {
        String login = (String) reqService.getRequest().getSession().getAttribute("user");
        reqService.getRequest().setAttribute("courses", courseService.getCoursesForStudent(login));
        NavigationService.navigateTo(reqService, "/pages/student/student-courses.jsp");
    }
}
