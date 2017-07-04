package controller;

import constants.ApplicationConstants;
import constants.ValidationConstants;
import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.Role;
import entities.User;
import org.apache.log4j.Logger;
import security.UserRoles;
import service.NavigationService;
import service.ServiceLoader;
import service.UserService;
import util.EncodingProvider;
import javax.servlet.http.HttpServletRequest;

public class StudentRegistrationController implements Controller {

    private static Logger LOGGER = Logger.getLogger(StudentRegistrationController.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        if(validateInputData(httpWrapper.getRequest())) {
            String login = httpWrapper.getRequest().getParameter("login");

            if (userService.getUserByLogin(login) == null) {
                User student = constructStudent(httpWrapper.getRequest());
                userService.addUser(student);
                NavigationService.redirectTo(httpWrapper, ApplicationConstants.BASE_APPLICATION_URL);
            } else {
                returnToPreviousPage(httpWrapper, "Selected login already in use!");
            }
        }
        else {
            returnToPreviousPage(httpWrapper, ApplicationConstants.INCORRECT_INPUT_DATA_MESSAGE);
        }
    }

    private User constructStudent(HttpServletRequest request) {
        User.Builder builder = User.newBuilder();

        builder.setLogin(request.getParameter("login"))
               .setFullName(request.getParameter("fullName"))
               .setEmail(request.getParameter("email"))
               .setPassword(EncodingProvider.encode(request.getParameter("password")))
               .setRole(new Role(UserRoles.STUDENT));

        return builder.build();
    }

    private boolean validateInputData(HttpServletRequest request) {
        String login = request.getParameter("login");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LOGGER.error(login.matches(ValidationConstants.LOGIN_REGEX));
        LOGGER.error(fullName.matches(ValidationConstants.NAME_REGEX));
        LOGGER.error( email.matches(ValidationConstants.EMAIL_REGEX));
        LOGGER.error( password.matches(ValidationConstants.PASSWORD_REGEX));

        return login.matches(ValidationConstants.LOGIN_REGEX) &&
               fullName.matches(ValidationConstants.NAME_REGEX) &&
               email.matches(ValidationConstants.EMAIL_REGEX) &&
               password.matches(ValidationConstants.PASSWORD_REGEX);
    }

    private void returnToPreviousPage(HttpWrapper httpWrapper, String message) {
        HttpServletRequest request = httpWrapper.getRequest();
        String login = request.getParameter("login");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");

        request.setAttribute("previousLogin", login);
        request.setAttribute("previousName", fullName);
        request.setAttribute("previousEmail", email);
        request.setAttribute("message", message);

        NavigationService.navigateTo(httpWrapper, "/app/registration");
    }
}
