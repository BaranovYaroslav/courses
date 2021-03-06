package persistence.dao;

import entities.User;
import entities.UserRole;

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
    public Optional<User> findByLogin(String login);

    /**
     * Method that extract user role from database.
     *
     * @param id id of user
     * @return Optional of user role
     */
    public Optional<UserRole> getUserRole(int id);

    /**
     * Method that extract user role from database.
     *
     * @param login user login
     * @return Optional of user role
     */
    public Optional<UserRole> getUserRole(String login);

    /**
     * Method that add user role into database.
     *
     * @param userId id of user
     * @param role role to be added
     */
    public void addRole(int userId, UserRole role);

    /**
     * Method that delete user role from database.
     *
     * @param userId id of user
     */
    public void deleteUserRole(int userId);
}
