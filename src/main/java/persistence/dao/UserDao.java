package persistence.dao;

import entities.Role;
import entities.User;

import java.util.Optional;

/**
 * Created by Ярослав on 11.04.2017.
 */
public interface UserDao extends AbstractDao<User>{

    public Optional<User> getUser(String login);

    public Optional<Role> getUserRole(int id);

    public Optional<Role> getUserRole(String login);

    public void addRole(int id, Role role);

    public void unregisterUsersFromCourse(int courseId);
}
