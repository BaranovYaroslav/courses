package persistence.dao.impl;

import entities.Role;
import entities.User;
import persistence.ConnectionManager;
import persistence.JdbcTemplate;
import persistence.dao.UserDao;
import org.apache.log4j.Logger;
import persistence.mappers.RoleMapper;
import persistence.mappers.StringMapper;
import persistence.mappers.UserMapper;

import java.util.List;

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
        int id = jdbcTemplate.insert("INSERT INTO `user` (`login`, `full_name`, `email`, `password`) " +
                            "VALUES (?, ?, ?, ?);", user.getLogin(), user.getFullName(),
                            user.getEmail(), user.getPassword());
        addRole(id, user.getRole());
        return id;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public User find(int id) {
        return jdbcTemplate.queryObject("SELECT `id`, `login`, `full_name`, `email`, `password`, `user_group`.`group` " +
                                        "FROM `user`" +
                                        "JOIN `user_group` ON `id`=`user_group`.`user_id` " +
                                        "WHERE `id`=?;", UserMapper::map, id);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.queryObjects("SELECT `id`, `login`, `full_name`, `email`, `password`, `user_group`.`group` " +
                "FROM `user`" +
                "JOIN `user_group` ON `id`=`user_group`.`user_id`;", UserMapper::map);
    }

    @Override
    public User getUser(String login) {
        return jdbcTemplate.queryObject("SELECT `id`, `login`, `full_name`, `email`, `password`, ug.`group` " +
                "FROM `user`" +
                "JOIN `user_group` ug ON `id`=ug.`user_id` " +
                "WHERE `login`=?;", UserMapper::map, login);
    }

    @Override
    public Role getUserRole(int id) {
        return jdbcTemplate.queryObject("SELECT * FROM `user_group` WHERE user_id=?;", RoleMapper::map, id);
    }

    @Override
    public void addRole(int id, Role role) {
        jdbcTemplate.insert("INSERT INTO `user_group` (`user_id`, `group`) VALUES (?, ?);", id, role.getRole());
    }

    @Override
    public String getUserRole(String login) {
        User user = getUser(login);
        return jdbcTemplate.queryObject("SELECT * FROM `user_group` WHERE user_id=?;", StringMapper::map, user.getId());
    }

    @Override
    public void unregisterUserFromCourse(int courseId) {
        jdbcTemplate.update("DELETE FROM `student_course` WHERE `course_id`=?;", courseId);
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
