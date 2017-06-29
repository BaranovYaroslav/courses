package controller;

import application.ApplicationConstants;
import application.ValidationConstants;
import dispatcher.Controller;
import dispatcher.HttpWrapper;

import entities.Role;
import entities.User;
import org.apache.log4j.Logger;
import security.UserRoles;
import service.NavigationService;
import service.NotificationService;
import service.ServiceLoader;
import service.UserService;
import service.impl.UserServiceImpl;
import util.EncodingProvider;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ярослав on 16.04.2017.
 */
public class NewProfessorController extends Controller {

    private Logger LOGGER = Logger.getLogger(NewProfessorController.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    @Override
    public void get(HttpWrapper httpWrapper) {
        String login = httpWrapper.getRequest().getParameter("login");

        if(validateInputData(httpWrapper)) {
            if (userService.getUserByLogin(login) == null) {
                User user = constructProfessor(httpWrapper.getRequest());
                userService.addUser(user);
                NavigationService.redirectTo(httpWrapper, "/app/admin");
            } else {
                returnToPreviousPage(httpWrapper, "Selected login already in use!");
            }
        } else {
            returnToPreviousPage(httpWrapper, ApplicationConstants.INCORRECT_INPUT_DATA_MESSAGE);
        }
    }

    private boolean validateInputData(HttpWrapper httpWrapper) {
        HttpServletRequest request = httpWrapper.getRequest();

        String login = request.getParameter("login");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

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

        NavigationService.navigateTo(httpWrapper, "/app/admin/new-professor");
    }

    private User constructProfessor(HttpServletRequest request) {
        User.Builder builder = User.newBuilder();

        builder.setLogin(request.getParameter("login"))
               .setFullName(request.getParameter("fullName"))
               .setEmail(request.getParameter("email"))
               .setPassword(EncodingProvider.encode(request.getParameter("password")))
               .setRole(new Role(UserRoles.PROFESSOR)) ;

        return builder.build();
    }
}
