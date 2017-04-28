package service.impl;

import dispatcher.HttpWrapper;
import entities.User;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import security.BaseResourceToRoleMapper;
import service.AuthenticationService;
import service.NavigationService;
import org.apache.log4j.Logger;
import util.EncodingProvider;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ярослав on 13.04.2017.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private static Logger LOGGER = Logger.getLogger(AuthenticationServiceImpl.class);

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
        User user = userDao.getUser(login);

        if(user == null) {
            return false;
        }

        return user.getPassword().equals(EncodingProvider.encode(password));
    }

    public void processCorrectLogin(HttpWrapper httpWrapper, String login) {
        login(httpWrapper.getRequest(), login);
        String baseUrl = BaseResourceToRoleMapper.getInstance().getBaseUrlForRole(userDao.getUserRole(login));
        NavigationService.redirectTo(httpWrapper, baseUrl);
    }

    public void processIncorrectLogin(HttpWrapper httpWrapper) {
        httpWrapper.getRequest().setAttribute("message", "Incorrect login or password");
        NavigationService.navigateTo(httpWrapper, "/app/login");
    }
}
