package controller;

import constants.*;
import dispatcher.Controller;
import dispatcher.HttpWrapper;

import org.apache.log4j.Logger;
import service.AuthenticationService;
import service.NavigationService;
import service.ServiceLoader;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class LoginController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoginController.class);

    private AuthenticationService authenticationService = ServiceLoader.getInstance().getService(AuthenticationService.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        String login = httpWrapper.getRequest().getParameter(RequestParameter.LOGIN);
        String password = httpWrapper.getRequest().getParameter(RequestParameter.PASSWORD);

        if(validateInputData(login, password)) {
            if(!authenticationService.checkLoginWithPassword(login, password)) {
                authenticationService.processIncorrectLogin(httpWrapper, login);
            }
            else{
                authenticationService.processCorrectLogin(httpWrapper, login);
            }
        }
        else {
            httpWrapper.getRequest().setAttribute(RequestAttribute.MESSAGE, Messages.INCORRECT_INPUT_DATA_MESSAGE);
            httpWrapper.getRequest().setAttribute(RequestAttribute.PREVIOUS_LOGIN, login);
            NavigationService.navigateTo(httpWrapper, NavigationConstants.LOGIN_URL);
        }
    }

    private boolean validateInputData(String login, String password) {
        return login.matches(ValidationConstants.LOGIN_REGEX) && password.matches(ValidationConstants.PASSWORD_REGEX);
    }
}
