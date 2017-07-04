package controller;

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
        httpWrapper.getRequest().setAttribute("types", CourseType.values());
        NavigationService.navigateTo(httpWrapper, "/pages/admin/new-course.jsp");
    }
}
