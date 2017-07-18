package controller;

import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.HttpWrapper;
import service.NavigationService;
import service.ProfessorService;
import service.ServiceLoader;

/**
 * Controller that load professor base page.
 *
 * @author Yaroslav Baranov
 */
public class LoadProfessorPageController implements Controller {

    private ProfessorService professorService;

    public LoadProfessorPageController() {
        professorService =ServiceLoader.getInstance().getService(ProfessorService.class);
    }

    public LoadProfessorPageController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    /**
     * Method that get professor login from session and forward
     * to page that represent all courses of this professor.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        String login = (String) httpWrapper.getRequest().getSession().getAttribute(RequestAttribute.USER);
        httpWrapper.getRequest().setAttribute(RequestAttribute.COURSES, professorService.getCoursesForProfessor(login));
        NavigationService.navigateTo(httpWrapper, NavigationConstants.PROFESSOR_PAGE);
    }
}
