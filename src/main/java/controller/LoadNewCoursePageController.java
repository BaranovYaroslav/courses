package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.CourseType;
import service.NavigationService;

/**
 * Created by Ярослав on 15.04.2017.
 */
public class LoadNewCoursePageController implements Controller {
    @Override
    public void execute(HttpWrapper httpWrapper) {
        httpWrapper.getRequest().setAttribute(RequestAttribute.TYPES, CourseType.values());
        NavigationService.navigateTo(httpWrapper, NavigationConstants.NEW_COURSE_PAGE);
    }
}
