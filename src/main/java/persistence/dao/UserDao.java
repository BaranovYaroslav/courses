package persistence.dao;

import entities.Role;
import entities.User;

import java.util.Optional;

/**
 * Interface for user data access object.
 *
 * @author Yaroslav Baranov
 */
public interface UserDao extends AbstractDao<User, Integer>{

    /**
     * Method that extract user from database.
     *
     * @param login login of user
     * @return Optional of found user
     */
    public Optional<User> getUser(String login);

    /**
     * Method that extract user role from database.
     *
     * @param id id of user
     * @return Optional of user role
     */
    public Optional<Role> getUserRole(int id);

    /**
     * Method that extract user role from database.
     *
     * @param login user login
     * @return Optional of user role
     */
    public Optional<Role> getUserRole(String login);

    /**
     * Method that role into database.
     *
     * @param userId id of user
     * @param role role to be added
     */
    public void addRole(int userId, Role role);
}
