package persistence.dao.impl;

import entities.Professor;
import persistence.ConnectionManager;
import persistence.JdbcTemplate;
import persistence.Query;
import persistence.dao.ProfessorDao;
import persistence.dao.UserDao;
import persistence.mappers.ProfessorMapper;

import java.util.List;
import java.util.Optional;

/**
 * Realization of ProfessorDao interface.
 *
 * @see persistence.dao.ProfessorDao
 * @author Yaroslav Baranov
 */
public class ProfessorJdbcDao implements ProfessorDao {

    private UserDao userDao;

    private JdbcTemplate jdbcTemplate;

    public ProfessorJdbcDao(ConnectionManager connectionManager, UserDao userDao) {
        this.userDao = userDao;
        jdbcTemplate = new JdbcTemplate(connectionManager);
    }

    @Override
    public Integer add(Professor professor) {
        return userDao.add(professor);
    }

    @Override
    public void delete(Professor professor) {
        userDao.delete(professor);
    }

    @Override
    public Integer update(Professor professor) {
        return userDao.update(professor);
    }

    @Override
    public Optional<Professor> find(Integer id) {
        return jdbcTemplate.queryObject(Query.FIND_USER_QUERY, ProfessorMapper::map, id);
    }

    @Override
    public Optional<Professor> findByLogin(String login) {
        return jdbcTemplate.queryObject(Query.FIND_USER_BY_LOGIN_QUERY, ProfessorMapper::map, login);
    }

    @Override
    public List<Professor> findAll() {
        return jdbcTemplate.queryObjects(Query.FIND_ALL_USERS_QUERY, ProfessorMapper::map);
    }
}
