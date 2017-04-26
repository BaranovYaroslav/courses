package persistence.dao;

import entities.Role;
import entities.User;

/**
 * Created by Ярослав on 11.04.2017.
 */
public interface UserDao extends AbstractDao<User>{

    public User getUserByLogin(String login);

    public Role getUserRole(int id);

    public String getUserRoleByLogin(String login);

    public void setRole(int id, Role role);

}
