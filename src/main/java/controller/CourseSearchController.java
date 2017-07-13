package controller;

import constants.*;
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
public class CourseSearchController implements Controller {

    private static Logger LOGGER = Logger.getLogger(CourseSearchController.class);

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        if(validateInputData(httpWrapper)) {
            String login = (String) httpWrapper.getRequest().getSession().getAttribute(RequestAttribute.USER);
            CourseSearchParameters searchParameters = constructSearchParametersFromRequest(httpWrapper);
            httpWrapper.getRequest().setAttribute(RequestAttribute.COURSES_FOR_REGISTRATION,
                    courseService.getCoursesForStudentWithSearch(login, searchParameters));
            goToStudentPage(httpWrapper);
        }
    }


    private boolean validateInputData(HttpWrapper httpWrapper) {
        String type = httpWrapper.getRequest().getParameter(RequestParameter.TYPE);
        String location = httpWrapper.getRequest().getParameter(RequestParameter.LOCATION);
        String min = httpWrapper.getRequest().getParameter(RequestParameter.MIN_COURSE_PRICE);
        String max = httpWrapper.getRequest().getParameter(RequestParameter.MAX_COURSE_PRICE);
        String onlyFree = httpWrapper.getRequest().getParameter(RequestParameter.ONLY_FREE_COURSES);

        return (type.length() == 0 || type.matches(ValidationConstants.WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX)) &&
               (location.length() == 0 || location.matches(ValidationConstants.WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX)) &&
               min.matches(ValidationConstants.POSITIVE_DOUBLE_REGEX) &&
               max.matches(ValidationConstants.POSITIVE_DOUBLE_REGEX) &&
               (onlyFree == null || onlyFree.equals(ControllerConstants.CHECKED_VALUE));
    }

    private CourseSearchParameters constructSearchParametersFromRequest(HttpWrapper httpWrapper) {
        CourseSearchParameters.Builder builder = CourseSearchParameters.newBuider();

        String type = httpWrapper.getRequest().getParameter(RequestParameter.TYPE);
        String location = httpWrapper.getRequest().getParameter(RequestParameter.LOCATION);
        String min = httpWrapper.getRequest().getParameter(RequestParameter.MIN_COURSE_PRICE);
        String max = httpWrapper.getRequest().getParameter(RequestParameter.MAX_COURSE_PRICE);
        String onlyFree = httpWrapper.getRequest().getParameter(RequestParameter.ONLY_FREE_COURSES);

        builder.setType(type)
               .setLocation(location)
               .setMinPrice(Double.parseDouble(min))
               .setMaxPrice(Double.parseDouble(max))
               .setOnlyFree(onlyFree != null);

        return builder.build();
    }

    private void goToStudentPage(HttpWrapper httpWrapper) {
        httpWrapper.getRequest().setAttribute(RequestAttribute.TYPES, CourseType.values());
        httpWrapper.getRequest().setAttribute(RequestAttribute.LOCATIONS, courseService.getDistinctCourseLocations());
        httpWrapper.getRequest().setAttribute(RequestAttribute.MAX_COURSE_PRICE, courseService.getMaxPriceOfCourse());
        NavigationService.navigateTo(httpWrapper, NavigationConstants.STUDENT_PAGE);
    }
}
