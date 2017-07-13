package controller;

import constants.NavigationConstants;
import dispatcher.HttpWrapper;
import service.NavigationService;

/**
 * Controller that registration page.
 *
 * @author Yaroslav Baranov
 */
public class LoadRegistrationPageController implements Controller{

    /**
     * Method that forward to registration page.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        NavigationService.navigateTo(httpWrapper, NavigationConstants.REGISTRATION_PAGE);
    }
}
