package controller;

import constants.NavigationConstants;
import dispatcher.HttpWrapper;
import service.NavigationService;

/**
 * Controller that load page to create new professor account.
 *
 * @author Yaroslav Baranov
 */
public class LoadNewProfessorPageController implements Controller {

    /**
     * Method that forward to page for creation of new professor account.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        NavigationService.navigateTo(httpWrapper, NavigationConstants.NEW_PROFESSOR_PAGE);
    }
}
