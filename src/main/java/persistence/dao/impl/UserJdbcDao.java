package persistence.dao.impl;

import entities.Role;
import entities.User;
import persistence.ConnectionManager;
import persistence.JdbcTemplate;
import persistence.Query;
import persistence.dao.UserDao;
import org.apache.log4j.Logger;
import persistence.exeptions.RuntimeSqlException;
import persistence.mappers.RoleMapper;
import persistence.mappers.StringMapper;
import persistence.mappers.UserMapper;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class UserJdbcDao implements UserDao {

    private static Logger LOGGER = Logger.getLogger(UserJdbcDao.class);

    private ConnectionManager connectionManager;

    private JdbcTemplate jdbcTemplate;

    public UserJdbcDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        jdbcTemplate = new JdbcTemplate(connectionManager);
    }

    @Override
    public int add(User user) {
        int id = jdbcTemplate.insert(Query.INSERT_USER_QUERY, user.getLogin(), user.getFullName(),
                            user.getEmail(), user.getPassword());
        addRole(id, user.getRole());
        return id;
    }

    @Override
    public void delete(User user) {
        jdbcTemplate.update(Query.DELETE_USER_QUERY, user.getId());
    }

    @Override
    public int update(User user) {
        return jdbcTemplate.update(Query.UPDATE_USER_QUERY, user.getLogin(), user.getFullName(), user.getEmail(),
                                                            user.getPassword(), user.getId());
    }

    @Override
    public Optional<User> find(int id) {
        return jdbcTemplate.queryObject(Query.FIND_USER_QUERY, UserMapper::map, id);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.queryObjects(Query.FIND_ALL_USERS_QUERY, UserMapper::map);
    }

    @Override
    public Optional<User> getUser(String login) {
        return jdbcTemplate.queryObject(Query.FIND_USER_BY_LOGIN_QUERY, UserMapper::map, login);
    }

    @Override
    public Optional<Role> getUserRole(int id) {
        return jdbcTemplate.queryObject(Query.GET_USER_ROLE_QUERY, RoleMapper::map, id);
    }

    @Override
    public void addRole(int id, Role role) {
        jdbcTemplate.insert(Query.INSERT_USER_ROLE_QUERY, id, role.getRole());
    }

    @Override
    public Optional<Role> getUserRole(String login) {
        Optional<User> user = getUser(login);
        if(user.isPresent()) {
            return jdbcTemplate.queryObject(Query.GET_USER_ROLE_BY_LOGIN_QUERY, RoleMapper::map, user.get().getId());
        }
        return Optional.empty();
    }

    @Override
    public void unregisterUsersFromCourse(int courseId) {
        jdbcTemplate.update(Query.UNREGISTER_ALL_STUDENTS_FROM_COURSE_QUERY, courseId);
    }
}
