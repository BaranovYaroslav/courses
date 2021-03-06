package service.impl;

import constants.Messages;
import constants.NavigationConstants;
import constants.RequestAttribute;
import dispatcher.HttpWrapper;
import entities.User;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import security.BaseResourceToRoleMapper;
import service.AuthenticationService;
import service.NavigationService;
import util.EncodingProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Realization of AuthenticationService interface.
 *
 * @see service.AuthenticationService
 * @author Yaroslav Baranov
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserDao userDao;

    public AuthenticationServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public AuthenticationServiceImpl(DaoFactory daoFactory) {
        userDao = daoFactory.getUserDao();
    }

    @Override
    public void login(HttpServletRequest request, String login) {
        request.getSession().setAttribute(RequestAttribute.USER, login);
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
        Optional<User> user = userDao.findByLogin(login);

        if(!user.isPresent()) {
            return false;
        }

        return user.get().getPassword().equals(EncodingProvider.encode(password));
    }

    public void processCorrectLogin(HttpWrapper httpWrapper, String login) {
        login(httpWrapper.getRequest(), login);
        String baseUrl = BaseResourceToRoleMapper.getInstance().getBaseUrlForRole(userDao.getUserRole(login).get());
        NavigationService.redirectTo(httpWrapper, baseUrl);
    }

    public void processIncorrectLogin(HttpWrapper httpWrapper, String login) {
        httpWrapper.getRequest().setAttribute(RequestAttribute.MESSAGE, Messages.ON_INCORRECT_LOGIN_MESSAGE);
        httpWrapper.getRequest().setAttribute(RequestAttribute.PREVIOUS_LOGIN, login);
        NavigationService.navigateTo(httpWrapper, NavigationConstants.LOGIN_URL);
    }
}
