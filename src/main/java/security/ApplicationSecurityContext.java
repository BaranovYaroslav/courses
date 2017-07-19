package security;

import constants.RequestAttribute;
import entities.User;
import entities.UserRole;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.dao.UserDao;
import persistence.dao.impl.UserJdbcDao;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Class that provide functionality that need to be secured.
 *
 * @author Yaroslav Baranov
 */
public class ApplicationSecurityContext {

    private Logger LOGGER = Logger.getLogger(ApplicationSecurityContext.class);

    private SecuredAccessStrategy securedAccessStrategy;

    private UserDao userDao = new UserJdbcDao(ConnectionManager.fromJndi("jdbc/courses"));

    public ApplicationSecurityContext() {}

    /**
     * Method that save user in session.
     *
     * @param request http request
     * @param login login of user
     */
    public void login(HttpServletRequest request, String login) {
        User user = userDao.findByLogin(login).get();
        request.getSession().setAttribute(RequestAttribute.USER, user.getLogin());
    }


    /**
     * Method that invalidate user session.
     *
     * @param request http request
     */
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    /**
     * Method determine if user is in given role.
     *
     * @param login login of user
     * @param role given role
     *
     * @return result of determination
     */
    public boolean isUserInRole(String login, UserRole role) {
        Optional<User> user = userDao.findByLogin(login);
        if(!user.isPresent()) {
            return false;
        }
        return user.get().getRole().equals(role);
    }


    /**
     * Method that return current user from session.
     *
     * @param request http request
     * @return login of user
     */
    public String getCurrentUser(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(RequestAttribute.USER);
    }

    public SecuredAccessStrategy getSecuredAccessStrategy() {
        return securedAccessStrategy;
    }

    public void setSecuredAccessStrategy(SecuredAccessStrategy securedAccessStrategy) {
        this.securedAccessStrategy = securedAccessStrategy;
    }
}
