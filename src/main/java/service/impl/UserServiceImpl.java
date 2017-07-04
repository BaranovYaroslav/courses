package service.impl;

import entities.Role;
import entities.User;
import org.apache.log4j.Logger;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
        userDao.add(user);
    }

    @Override
    public User getUserByLogin(String login) {
        User user = userDao.getUser(login);
        if(user == null) {
            return null;
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public boolean userHasRole(String login, String role) {
        User user = getUserByLogin(login);

        if(user == null){
            return false;
        }

        if(user.getRole().getRole().equals(role)) {
            return true;
        }

        return false;
    }

    @Override
    public Role getRole(int id) {
        return userDao.getUserRole(id);
    }

    @Override
    public String getRole(String login) {
        return userDao.getUserRole(login).getRole();
    }
}
