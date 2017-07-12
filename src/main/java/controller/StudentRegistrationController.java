package controller;

import constants.*;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Role;
import entities.User;
import org.apache.log4j.Logger;
import security.UserRole;
import service.NavigationService;
import service.ServiceLoader;
import service.UserService;
import sun.rmi.runtime.Log;
import util.EncodingProvider;
import javax.servlet.http.HttpServletRequest;

public class StudentRegistrationController implements Controller {

    private static Logger LOGGER = Logger.getLogger(StudentRegistrationController.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    public StudentRegistrationController() {}

    public StudentRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(HttpWrapper httpWrapper) {
        LOGGER.error(1111);
        if(validateInputData(httpWrapper.getRequest())) {
            String login = httpWrapper.getRequest().getParameter(RequestParameter.LOGIN);

            if (userService.getUserByLogin(login) == null) {
                LOGGER.error(2222);
                User student = constructStudent(httpWrapper.getRequest());
                userService.addUser(student);
                NavigationService.redirectTo(httpWrapper, ApplicationConstants.BASE_APPLICATION_URL);
            } else {
                LOGGER.error(3333);
                returnToPreviousPage(httpWrapper, Messages.ON_LOGIN_OCCUPIED_MESSAGE);
            }
        }
        else {
            LOGGER.error(4444);
            returnToPreviousPage(httpWrapper, ApplicationConstants.INCORRECT_INPUT_DATA_MESSAGE);
        }
    }

    private User constructStudent(HttpServletRequest request) {
        User.Builder builder = User.newBuilder();

        builder.setLogin(request.getParameter(RequestParameter.LOGIN))
               .setFullName(request.getParameter(RequestParameter.FULL_NAME))
               .setEmail(request.getParameter(RequestParameter.EMAIL))
               .setPassword(EncodingProvider.encode(request.getParameter(RequestParameter.PASSWORD)))
               .setRole(new Role(UserRole.STUDENT));

        return builder.build();
    }

    private boolean validateInputData(HttpServletRequest request) {
        String login = request.getParameter(RequestParameter.LOGIN);
        String fullName = request.getParameter(RequestParameter.FULL_NAME);
        String email = request.getParameter(RequestParameter.EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);

        return login.matches(ValidationConstants.LOGIN_REGEX) &&
               fullName.matches(ValidationConstants.NAME_REGEX) &&
               email.matches(ValidationConstants.EMAIL_REGEX) &&
               password.matches(ValidationConstants.PASSWORD_REGEX);
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

        NavigationService.navigateTo(httpWrapper, NavigationConstants.STUDENT_REGISTRATION_URL);
    }
}
