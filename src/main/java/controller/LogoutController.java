package controller;

import constants.ApplicationConstants;
import dispatcher.HttpWrapper;
import service.AuthenticationService;
import service.NavigationService;
import service.ServiceLoader;

/**
 * Controller of logout case.
 *
 * @author Yaroslav Baranov
 */
public class LogoutController implements Controller {

    private AuthenticationService authenticationService;

    public LogoutController() {
        authenticationService = ServiceLoader.getInstance().getService(AuthenticationService.class);
    }

    public LogoutController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Method invalidate user session and forward base page.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        authenticationService.logout(httpWrapper.getRequest());
        NavigationService.navigateTo(httpWrapper, ApplicationConstants.BASE_APPLICATION_URL);
    }
}
