package persistence.dao;

import entities.Role;
import entities.User;

/**
 * Created by Ярослав on 11.04.2017.
 */
public interface UserDao extends AbstractDao<User>{

    public User getUser(String login);

    public Role getUserRole(int id);

    public Role getUserRole(String login);

    public void addRole(int id, Role role);

    public void unregisterUserFromCourse(int courseId);
}
