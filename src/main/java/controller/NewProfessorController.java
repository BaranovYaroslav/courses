package controller;

import constants.*;
import dispatcher.HttpWrapper;

import entities.Professor;
import entities.User;
import entities.UserRole;
import org.apache.log4j.Logger;
import service.NavigationService;
import service.ServiceLoader;
import service.UserService;
import util.EncodingProvider;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller that provide to admin possibility to create new professor account.
 *
 * @author Yaroslav Baranov
 */
public class NewProfessorController implements Controller {

    private static Logger LOGGER = Logger.getLogger(NewProfessorController.class);

    private UserService userService;

    public NewProfessorController() {
        userService = ServiceLoader.getInstance().getService(UserService.class);
    }

    public NewProfessorController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method that provide creation of new professor account.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        String login = httpWrapper.getRequest().getParameter(RequestParameter.LOGIN);

        if(validateInputData(httpWrapper)) {
            if (userService.getUserByLogin(login) == null) {
                Professor professor = constructProfessor(httpWrapper.getRequest());
                userService.addUser(professor);
                LOGGER.info(LoggerMessage.ON_NEW_PROFESSOR_ACCOUNT_CREATED_MESSAGE + professor);
                NavigationService.redirectTo(httpWrapper, NavigationConstants.ADMIN_ROOT_URL);
            } else {
                returnToPreviousPage(httpWrapper, Messages.ON_LOGIN_OCCUPIED_MESSAGE);
            }
        } else {
            returnToPreviousPage(httpWrapper, Messages.INCORRECT_INPUT_DATA_MESSAGE);
        }
    }

    /**
     * Method that provide validation of request parameters.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     * @see constants.ValidationConstants
     */
    private boolean validateInputData(HttpWrapper httpWrapper) {
        HttpServletRequest request = httpWrapper.getRequest();

        String login = request.getParameter(RequestParameter.LOGIN);
        String fullName = request.getParameter(RequestParameter.FULL_NAME);
        String email = request.getParameter(RequestParameter.EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);
        String repeatedPassword = request.getParameter(RequestParameter.REPEATED_PASSWORD);

        return login.matches(ValidationConstants.LOGIN_REGEX) &&
               fullName.matches(ValidationConstants.NAME_REGEX) &&
               email.matches(ValidationConstants.EMAIL_REGEX) &&
               password.matches(ValidationConstants.PASSWORD_REGEX) &&
               password.equals(repeatedPassword);
    }

    /**
     * Method that construct new professor account from request parameters.
     *
     * @param request http request that contains parameters of new course.
     */
    private Professor constructProfessor(HttpServletRequest request) {
        Professor.Builder builder = Professor.newBuilder();

        builder.setLogin(request.getParameter(RequestParameter.LOGIN))
               .setFullName(request.getParameter(RequestParameter.FULL_NAME))
               .setEmail(request.getParameter(RequestParameter.EMAIL))
               .setPassword(EncodingProvider.encode(request.getParameter(RequestParameter.PASSWORD)))
               .setRole(UserRole.PROFESSOR) ;

        return builder.build();
    }

    private void returnToPreviousPage(HttpWrapper httpWrapper, String message) {
        HttpServletRequest request = httpWrapper.getRequest();
        String login = request.getParameter(RequestParameter.LOGIN);
        String fullName = request.getParameter(RequestParameter.FULL_NAME);
        String email = request.getParameter(RequestParameter.EMAIL);

        request.setAttribute(RequestAttribute.PREVIOUS_LOGIN, login);
        request.setAttribute(RequestAttribute.PREVIOUS_FULL_NAME, fullName);
        request.setAttribute(RequestAttribute.PREVIOUS_EMAIL, email);
        request.setAttribute(RequestAttribute.MESSAGE, message);

        NavigationService.navigateTo(httpWrapper, NavigationConstants.NEW_PROFESSOR_PAGE);
    }
}
