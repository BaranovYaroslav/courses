package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import constants.RequestParameter;
import dispatcher.HttpWrapper;
import persistence.dao.CourseDao;
import persistence.dao.factory.DaoFactory;
import service.CourseService;
import service.NavigationService;
import service.ServiceLoader;

/**
 * Created by Ярослав on 15.07.2017.
 */
public class CourseDetailsController implements Controller {

    private CourseService courseService = ServiceLoader.getInstance().getService(CourseService.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        Integer courseId = Integer.parseInt(httpWrapper.getRequest().getParameter(RequestParameter.COURSE_ID));
        httpWrapper.getRequest().setAttribute(RequestAttribute.COURSE, courseService.getCourse(courseId));
        NavigationService.navigateTo(httpWrapper, NavigationConstants.COURSE_DETAILS_PAGE);
    }
}
