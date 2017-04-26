package controller;

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

        if(userService.getUserByLogin(login) == null) {
            User user = constructProfessor(httpWrapper.getRequest());
            user.setRole(new Role(UserRoles.PROFESSOR));
            userService.addUser(user);
            NavigationService.redirectTo(httpWrapper, "/app/admin");
        }
        else {
            httpWrapper.getRequest().setAttribute("message", "Selected login already in use!");
            NavigationService.navigateTo(httpWrapper, "/app/admin/new-professor");
        }

    }

    private User constructProfessor(HttpServletRequest request) {
        User user = new User();

        user.setLogin(request.getParameter("login"));
        user.setFullName(request.getParameter("fullName"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));

        return user;
    }
}
