package persistence.dao.impl;

import entities.Student;
import entities.User;
import persistence.ConnectionManager;
import persistence.JdbcTemplate;
import persistence.Query;
import persistence.dao.StudentDao;
import persistence.dao.UserDao;
import persistence.dao.factory.JdbcDaoFactory;
import persistence.mappers.StudentMapper;
import persistence.mappers.UserMapper;

import java.util.List;
import java.util.Optional;

/**
 * Realization of StudentDao interface.
 *
 * @see persistence.dao.StudentDao
 * @author Yaroslav Baranov
 */
public class StudentJdbcDao implements StudentDao {

    private UserDao userDao;

    private JdbcTemplate jdbcTemplate;

    public StudentJdbcDao(ConnectionManager connectionManager, UserDao userDao) {
        this.userDao = userDao;
        jdbcTemplate = new JdbcTemplate(connectionManager);
    }

    @Override
    public Integer add(Student student) {
        return userDao.add(student);
    }

    @Override
    public void delete(Student student) {
        userDao.delete(student);
    }

    @Override
    public Integer update(Student student) {
        return userDao.update(student);
    }

    @Override
    public Optional<Student> find(Integer id) {
        return jdbcTemplate.queryObject(Query.FIND_USER_QUERY, StudentMapper::map, id);
    }

    @Override
    public Optional<Student> findByLogin(String login) {
        return jdbcTemplate.queryObject(Query.FIND_USER_BY_LOGIN_QUERY, StudentMapper::map, login);
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.queryObjects(Query.FIND_ALL_USERS_QUERY, StudentMapper::map);
    }
}
