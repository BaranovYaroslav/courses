package controller;

import constants.*;
import dispatcher.HttpWrapper;

import org.apache.log4j.Logger;
import service.AuthenticationService;
import service.NavigationService;
import service.ServiceLoader;
import service.StudentService;

/**
 * Controller of login case.
 *
 * @author Yaroslav Baranov
 */
public class LoginController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LoginController.class);

    private AuthenticationService authenticationService;

    public LoginController() {
        authenticationService = ServiceLoader.getInstance().getService(AuthenticationService.class);
    }

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Method that compare user inputs with account info and provide processing of login case.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        String login = httpWrapper.getRequest().getParameter(RequestParameter.LOGIN);
        String password = httpWrapper.getRequest().getParameter(RequestParameter.PASSWORD);

        if(validateInputData(login, password)) {
            if(!authenticationService.checkLoginWithPassword(login, password)) {
                authenticationService.processIncorrectLogin(httpWrapper, login);
            }
            else{
                LOGGER.warn("Unsuccessful login:" + login + " " + password);
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
