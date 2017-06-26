package service;

import dispatcher.HttpWrapper;
import entities.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ярослав on 13.04.2017.
 */
public interface AuthenticationService {

    public void login(HttpServletRequest request, String login);

    public void logout(HttpServletRequest request);

    boolean register(User user);

    public boolean checkLoginWithPassword(String login, String password);

    public void processCorrectLogin(HttpWrapper httpWrapper, String login);

    public void processIncorrectLogin(HttpWrapper httpWrapper, String login);
}
