package service;

import dispatcher.HttpWrapper;
import entities.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface of service to provide authentication.
 *
 * @author Yaroslav Baranov
 */
public interface AuthenticationService {

    /**
     * Method that save user in session.
     *
     * @param request http request
     * @param login login of user
     */
    public void login(HttpServletRequest request, String login);

    /**
     * Method that invalidate user session.
     *
     * @param request http request
     */
    public void logout(HttpServletRequest request);

    /**
     * Method for user registration.
     *
     * @param user user to register
     * @return result of registration
     */
    public boolean register(User user);

    /**
     * Method that compare given password to actual password.
     *
     * @param login login of user
     * @param password password of user
     * @return result of comparison
     */
    public boolean checkLoginWithPassword(String login, String password);

    /**
     * Method that process correct user login to system.
     *
     * @param httpWrapper holder of http request and response
     * @param login login og user
     */
    public void processCorrectLogin(HttpWrapper httpWrapper, String login);

    /**
     * Method that process incorrect user login to system.
     *
     * @param httpWrapper holder of http request and response
     * @param login login og user
     */
    public void processIncorrectLogin(HttpWrapper httpWrapper, String login);
}
