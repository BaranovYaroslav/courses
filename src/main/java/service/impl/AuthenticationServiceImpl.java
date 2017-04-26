package service.impl;

import entities.User;
import persistence.ConnectionManager;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import persistence.dao.impl.UserJdbcDao;
import service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ярослав on 13.04.2017.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserDao userDao;

    public AuthenticationServiceImpl(DaoFactory daoFactory) {
        userDao = daoFactory.getUserDao();
    }

    @Override
    public void login(HttpServletRequest request, String login) {
        request.getSession().setAttribute("user", login);
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @Override
    public boolean register(User user) {
        return false;
    }

    @Override
    public boolean checkLoginWithPassword(String login, String password) {
        User user = userDao.getUserByLogin(login);

        if(user == null) {
            return false;
        }

        return user.getPassword().equals(password);
    }

}
