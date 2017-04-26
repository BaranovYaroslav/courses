package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import org.apache.log4j.Logger;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;
import service.impl.CourseServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;

public class LoadEditCoursePageController extends Controller {

    private static Logger LOGGER = Logger.getLogger(LoginController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void get(HttpWrapper reqService) {
        LOGGER.error("In edit controller");
        try {
            int id = Integer.parseInt(reqService.getRequest().getParameter("id"));
            reqService.getRequest().setAttribute("course", courseService.getCourse(id));
            reqService.getRequest().getRequestDispatcher("/pages/admin/edit-course.jsp")
                    .forward(reqService.getRequest(), reqService.getResponse());
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
