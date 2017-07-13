package service;

import entities.Role;
import entities.User;

import java.util.List;

/**
 * Interface of service to provide business options with user.
 *
 * @author Yaroslav Baranov
 */
public interface UserService {

    /**
     * Method to add new user to database.
     *
     * @param user user to be added
     */
    public void addUser(User user);

    /**
     * Method to get user by login.
     *
     * @param login login of user
     * @return found user
     */
    public User getUserByLogin(String login);

    /**
     * Method to get all users.
     *
     * @return list of users
     */
    public List<User> getAllUsers();

    /**
     * Method to determinate is given user has given role.
     *
     * @param login given login of user
     * @param role given role
     * @return result of determination
     */
    public boolean userHasRole(String login, String role);
}
