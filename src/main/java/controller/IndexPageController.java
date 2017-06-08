package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.CourseService;
import service.ServiceLoader;
import service.UserService;

import java.io.IOException;

/**
 * Created by Ярослав on 08.06.2017.
 */
public class IndexPageController extends Controller {
    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    @Override
    public void get(HttpWrapper httpWrapper) {
        try {
            httpWrapper.getResponse().getWriter().write(getInformationAsJSON());
            httpWrapper.getResponse().getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getInformationAsJSON() {
        return String.format("{\"courseNumber\": %d," +
                              "\"studentNumber\": %d," +
                              "\"professorNumber\": %d}", courseService.getCourses().size(),
                                                                    userService.getAllUsers().size(),
                                                                    userService.getAllUsers().size());
    }
}
