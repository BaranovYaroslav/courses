package service.impl;

import entities.Role;
import entities.User;
import org.apache.log4j.Logger;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserServiceImpl(DaoFactory daoFactory) {
        this.userDao = daoFactory.getUserDao();
    }

    @Override
    public void addUser(User user) {
        userDao.add(user);
    }

    @Override
    public User getUserByLogin(String login) {
        Optional<User> user = userDao.getUser(login);
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
        return userDao.getUserRole(id).get();
    }

    @Override
    public String getRole(String login) {
        return userDao.getUserRole(login).get().getRole();
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
