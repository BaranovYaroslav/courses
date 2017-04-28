package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;

import org.apache.log4j.Logger;
import security.BaseResourceToRoleMapper;
import security.UserPrincipal;
import service.AuthenticationService;
import service.NavigationService;
import service.ServiceLoader;
import service.UserService;
import service.impl.AuthenticationServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class LoginController extends Controller {

    private static Logger LOGGER = Logger.getLogger(LoginController.class);

    private AuthenticationService authenticationService = ServiceLoader.getInstance().getService(AuthenticationService.class);

    private UserService userService = ServiceLoader.getInstance().getService(UserService.class);

    @Override
    public void get(HttpWrapper httpWrapper) {
        String login = httpWrapper.getRequest().getParameter("login");
        String password = httpWrapper.getRequest().getParameter("password");

        if(!authenticationService.checkLoginWithPassword(login, password)) {
            authenticationService.processIncorrectLogin(httpWrapper);
        }

        else{
            authenticationService.processCorrectLogin(httpWrapper, login);
        }
    }
}
