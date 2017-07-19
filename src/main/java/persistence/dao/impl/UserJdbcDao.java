package persistence.dao.impl;

import entities.User;
import entities.UserRole;
import persistence.ConnectionManager;
import persistence.JdbcTemplate;
import persistence.Query;
import persistence.dao.UserDao;
import org.apache.log4j.Logger;
import persistence.mappers.RoleMapper;
import persistence.mappers.UserMapper;

import java.util.List;
import java.util.Optional;

/**
 * Realization of UserDao interface.
 *
 * @see persistence.dao.UserDao
 * @author Yaroslav Baranov
 */
public class UserJdbcDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserJdbcDao(ConnectionManager connectionManager) {
        jdbcTemplate = new JdbcTemplate(connectionManager);
    }

    @Override
    public Integer add(User user) {
        int id = jdbcTemplate.insert(Query.INSERT_USER_QUERY, user.getLogin(), user.getFullName(),
                            user.getEmail(), user.getPassword());
        addRole(id, user.getRole());
        return id;
    }

    @Override
    public void delete(User user) {
        deleteUserRole(user.getId());
        jdbcTemplate.update(Query.DELETE_USER_QUERY, user.getId());
    }

    @Override
    public Integer update(User user) {
        return jdbcTemplate.update(Query.UPDATE_USER_QUERY, user.getLogin(), user.getFullName(), user.getEmail(),
                                                            user.getPassword(), user.getId());
    }

    @Override
    public Optional<User> find(Integer id) {
        return jdbcTemplate.queryObject(Query.FIND_USER_QUERY, UserMapper::map, id);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.queryObjects(Query.FIND_ALL_USERS_QUERY, UserMapper::map);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return jdbcTemplate.queryObject(Query.FIND_USER_BY_LOGIN_QUERY, UserMapper::map, login);
    }

    @Override
    public Optional<UserRole> getUserRole(int id) {
        return jdbcTemplate.queryObject(Query.GET_USER_ROLE_QUERY, RoleMapper::map, id);
    }

    @Override
    public void addRole(int id, UserRole role) {
        jdbcTemplate.insert(Query.INSERT_USER_ROLE_QUERY, id, role.getRole());
    }

    @Override
    public void deleteUserRole(int userId) {
        jdbcTemplate.update(Query.DELETE_USER_ROLE_QUERY, userId);
    }

    @Override
    public Optional<UserRole> getUserRole(String login) {
        Optional<User> user = findByLogin(login);
        if(user.isPresent()) {
            return jdbcTemplate.queryObject(Query.GET_USER_ROLE_BY_LOGIN_QUERY, RoleMapper::map, user.get().getId());
        }
        return Optional.empty();
    }
}
