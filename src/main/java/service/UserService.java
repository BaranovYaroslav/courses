package service;

import entities.Role;
import entities.User;

import java.util.List;

/**
 * Created by Ярослав on 15.04.2017.
 */
public interface UserService {

    public void addUser(User user);

    public User getUserByLogin(String login);

    public List<User> getAllUsers();

    public boolean userHasRole(String login, String role);

    public Role getRole(int id);

    public String getRole(String login);

}
