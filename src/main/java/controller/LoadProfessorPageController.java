package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import org.apache.log4j.Logger;
import service.CourseService;
import service.NavigationService;
import service.ProfessorService;
import service.ServiceLoader;
import service.impl.CourseServiceImpl;

/**
 * Created by Ярослав on 17.04.2017.
 */
public class LoadProfessorPageController implements Controller {

    private ProfessorService professorService = ServiceLoader.getInstance().getService(ProfessorService.class);

    @Override
    public void execute(HttpWrapper reqService) {
        String login = (String) reqService.getRequest().getSession().getAttribute(RequestAttribute.USER);
        reqService.getRequest().setAttribute(RequestAttribute.COURSES, professorService.getCoursesForProfessor(login));
        NavigationService.navigateTo(reqService, NavigationConstants.PROFESSOR_PAGE);
    }
}
