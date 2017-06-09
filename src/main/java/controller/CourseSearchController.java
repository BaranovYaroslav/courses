package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import org.apache.log4j.Logger;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;
import service.util.CourseSearchParameters;

/**
 * Created by Ярослав on 09.06.2017.
 */
public class CourseSearchController extends Controller {

    private static Logger LOGGER = Logger.getLogger(CourseSearchController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void get(HttpWrapper httpWrapper) {
        String login = (String) httpWrapper.getRequest().getSession().getAttribute("user");
        CourseSearchParameters searchParameters = constructSearchParametersFromRequest(httpWrapper);
        httpWrapper.getRequest().setAttribute("coursesForRegistration",
                                               courseService.getCoursesForStudentWithSearch(login, searchParameters));
        NavigationService.navigateTo(httpWrapper, "/pages/student/student.jsp");
    }

    private CourseSearchParameters constructSearchParametersFromRequest(HttpWrapper httpWrapper) {
        CourseSearchParameters parameters = new CourseSearchParameters();

        String type = httpWrapper.getRequest().getParameter("type");
        String location = httpWrapper.getRequest().getParameter("location");
        String min = httpWrapper.getRequest().getParameter("minPrice");
        String max = httpWrapper.getRequest().getParameter("maxPrice");
        String onlyFree = httpWrapper.getRequest().getParameter("onlyFree");

        parameters.setType(type)
                  .setLocation(location)
                  .setMinPrice(Integer.parseInt(min))
                  .setMaxPrice(Integer.parseInt(max))
                  .setOnlyFree(onlyFree.equals(ControllerConstants.CHECKED_VALUE));

        return parameters;
    }
}
