package service.impl;

import entities.User;
import entities.UserRole;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import service.UserService;

import java.util.List;
import java.util.Optional;

/**
 * Realization of UserService interface.
 *
 * @see service.UserService
 * @author Yaroslav Baranov
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserServiceImpl(DaoFactory daoFactory) {
        this.userDao = daoFactory.getUserDao();
    }

    @Override
    public synchronized void addUser(User user) {
        if(!userDao.findByLogin(user.getLogin()).isPresent()) {
            userDao.add(user);
        }
    }

    @Override
    public User getUserByLogin(String login) {
        Optional<User> user = userDao.findByLogin(login);
        if(!user.isPresent()) {
            return null;
        }

        return user.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public boolean userHasRole(String login, UserRole role) {
        User user = getUserByLogin(login);

        if(user == null){
            return false;
        }

        if(user.getRole().equals(role)) {
            return true;
        }

        return false;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
