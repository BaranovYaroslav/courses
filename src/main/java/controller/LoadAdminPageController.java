package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;

import org.apache.log4j.Logger;
import service.impl.CourseServiceImpl;

public class LoadAdminPageController extends Controller {

    private static Logger LOGGER = Logger.getLogger(LoadAdminPageController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void get(HttpWrapper httpWrapper) {
        httpWrapper.getRequest().setAttribute("courses", courseService.getCourses());
        NavigationService.navigateTo(httpWrapper, "/pages/admin/admin.jsp");
    }
}
