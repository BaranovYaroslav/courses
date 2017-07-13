package controller;

import constants.NavigationConstants;
import dispatcher.HttpWrapper;
import org.apache.log4j.Logger;
import service.NavigationService;

/**
 * Controller that load login page.
 *
 * @author Yaroslav Baranov
 */
public class LoadLoginPageController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoadLoginPageController.class);

    /**
     * Method that forward to login page.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        NavigationService.navigateTo(httpWrapper, NavigationConstants.LOGIN_PAGE);
    }
}
