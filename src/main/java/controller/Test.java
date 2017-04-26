package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import entities.User;
import persistence.ConnectionManager;
import persistence.dao.UserDao;
import persistence.dao.impl.UserJdbcDao;

import java.io.IOException;

/**
 * Created by Ярослав on 24.04.2017.
 */
public class Test extends Controller {

    @Override
    public void get(HttpWrapper reqService) {
        UserDao userDao = new UserJdbcDao(ConnectionManager.fromJndi("jdbc/project"));
        try {
            String result = "";
            result += userDao.findAll().size();
            for(User user: userDao.findAll()) {
                result += user + "\n";
            }
            reqService.getResponse().getWriter().println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
