package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import org.apache.log4j.Logger;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;
import service.impl.CourseServiceImpl;

/**
 * Created by Ярослав on 16.04.2017.
 */
public class DeleteCourseController extends Controller {

    private static Logger LOGGER = Logger.getLogger(DeleteCourseController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void get(HttpWrapper reqService) {
        Integer id = Integer.parseInt(reqService.getRequest().getParameter("id"));
        courseService.deleteCourse(id);
        NavigationService.redirectTo(reqService, "/app/admin");
    }
}
