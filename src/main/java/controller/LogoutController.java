package controller;

import application.ApplicationConstants;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.AuthenticationService;
import service.NavigationService;
import service.ServiceLoader;
import service.impl.AuthenticationServiceImpl;

/**
 * Created by Ярослав on 21.04.2017.
 */
public class LogoutController extends Controller {

    private AuthenticationService authenticationService = ServiceLoader.getInstance().getService(AuthenticationService.class);

    @Override
    public void get(HttpWrapper httpWrapper) {
        authenticationService.logout(httpWrapper.getRequest());
        NavigationService.navigateTo(httpWrapper, ApplicationConstants.BASE_APPLICATION_URL);
    }
}
