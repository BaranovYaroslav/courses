package security;

import entities.User;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.dao.UserDao;
import persistence.dao.impl.UserJdbcDao;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ярослав on 13.04.2017.
 */
public class ApplicationSecurityContext {

    private Logger LOGGER = Logger.getLogger(ApplicationSecurityContext.class);

    private SecuredAccessStrategy securedAccessStrategy;

    private UserDao userDao = new UserJdbcDao(ConnectionManager.fromJndi("jdbc/project"));

    public ApplicationSecurityContext() {}

    public void login(HttpServletRequest request, String login, String password) {
        User userFromDB = userDao.getUser(login);
        request.getSession().setAttribute("user", userFromDB.getLogin());
    }

    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    public boolean isUserInRole(String login, String role) {
        User user = userDao.getUser(login);
        if(user == null) {
            return false;
        }
        return userDao.getUserRole(login).equals(role);
    }

    public String getCurrentUser(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("user");
    }

    public SecuredAccessStrategy getSecuredAccessStrategy() {
        return securedAccessStrategy;
    }

    public void setSecuredAccessStrategy(SecuredAccessStrategy securedAccessStrategy) {
        this.securedAccessStrategy = securedAccessStrategy;
    }
}
