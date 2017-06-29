package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import org.apache.log4j.Logger;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;
import service.impl.CourseServiceImpl;

/**
 * Created by Ярослав on 17.04.2017.
 */
public class LoadProfessorPageController extends Controller {

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void get(HttpWrapper reqService) {
        String login = (String) reqService.getRequest().getSession().getAttribute("user");
        reqService.getRequest().setAttribute("courses", courseService.getCoursesForProfessor(login));
        NavigationService.navigateTo(reqService, "/pages/professor/professor.jsp");
    }
}
