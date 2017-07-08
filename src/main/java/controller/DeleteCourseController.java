package controller;

import constants.RequestParameter;
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
public class DeleteCourseController implements Controller {

    private static Logger LOGGER = Logger.getLogger(DeleteCourseController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void execute(HttpWrapper reqService) {
        Integer id = Integer.parseInt(reqService.getRequest().getParameter(RequestParameter.ID));
        courseService.deleteCourse(id);
        NavigationService.redirectTo(reqService, "/app/admin");
    }
}
