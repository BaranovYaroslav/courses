package controller;

import constants.ControllerConstants;
import constants.ValidationConstants;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.CourseType;
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
        if(validateInputData(httpWrapper)) {
            String login = (String) httpWrapper.getRequest().getSession().getAttribute("user");
            CourseSearchParameters searchParameters = constructSearchParametersFromRequest(httpWrapper);
            httpWrapper.getRequest().setAttribute("coursesForRegistration",
                    courseService.getCoursesForStudentWithSearch(login, searchParameters));
            goToStudentPage(httpWrapper);
        }
    }


    private boolean validateInputData(HttpWrapper httpWrapper) {
        String type = httpWrapper.getRequest().getParameter("type");
        String location = httpWrapper.getRequest().getParameter("location");
        String min = httpWrapper.getRequest().getParameter("minPrice");
        String max = httpWrapper.getRequest().getParameter("maxPrice");
        String onlyFree = httpWrapper.getRequest().getParameter("onlyFree");

        return (type.length() == 0 || type.matches(ValidationConstants.WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX)) &&
               (location.length() == 0 || location.matches(ValidationConstants.WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX)) &&
               min.matches(ValidationConstants.POSITIVE_DOUBLE_REGEX) &&
               max.matches(ValidationConstants.POSITIVE_DOUBLE_REGEX) &&
               (onlyFree == null || onlyFree.equals(ControllerConstants.CHECKED_VALUE));
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
                  .setMinPrice(Double.parseDouble(min))
                  .setMaxPrice(Double.parseDouble(max))
                  .setOnlyFree(onlyFree != null);

        return parameters;
    }

    private void goToStudentPage(HttpWrapper httpWrapper) {
        httpWrapper.getRequest().setAttribute("types", CourseType.values());
        httpWrapper.getRequest().setAttribute("locations", courseService.getDistinctCourseLocations());
        httpWrapper.getRequest().setAttribute("maxPrice", courseService.getMaxPriceOfCourse());
        NavigationService.navigateTo(httpWrapper, "/pages/student/student.jsp");
    }
}
