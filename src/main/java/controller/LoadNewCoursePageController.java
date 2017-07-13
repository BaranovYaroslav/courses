package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.HttpWrapper;
import entities.CourseType;
import service.NavigationService;

/**
 * Controller that load page to create new course.
 *
 * @author Yaroslav Baranov
 */
public class LoadNewCoursePageController implements Controller {

    /**
     * Method that forward to page for creation of new course.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        httpWrapper.getRequest().setAttribute(RequestAttribute.TYPES, CourseType.values());
        NavigationService.navigateTo(httpWrapper, NavigationConstants.NEW_COURSE_PAGE);
    }
}
