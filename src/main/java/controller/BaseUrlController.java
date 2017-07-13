package controller;

import constants.NavigationConstants;
import dispatcher.HttpWrapper;
import service.NavigationService;

/**
 * Controller for root url.
 *
 * @author Yaroslav Baranov
 */
public class BaseUrlController implements Controller {

    /**
     * Method that redirect user to index page.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        NavigationService.redirectTo(httpWrapper, NavigationConstants.INDEX_PAGE);
    }
}
