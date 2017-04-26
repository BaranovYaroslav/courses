package persistence.dao.impl;

import entities.Role;
import entities.Student;
import entities.User;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.JdbcTemplate;
import persistence.dao.StudentDao;
import security.UserRoles;

import java.util.List;

/**
 * Created by Ярослав on 21.04.2017.
 */
public class StudentJdbcDao implements StudentDao {

    private static Logger LOGGER = Logger.getLogger(UserJdbcDao.class);

    private ConnectionManager connectionManager;

    private JdbcTemplate jdbcTemplate;

    public StudentJdbcDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        jdbcTemplate = new JdbcTemplate(connectionManager);
    }

    @Override
    public int add(Student student) {
        int id = jdbcTemplate.insert("INSERT INTO `user` (`login`, `full_name`, `email`, `password`) " +
                        "VALUES (?, ?, ?, ?);", student.getLogin(), student.getFullName(),
                student.getEmail(), student.getPassword());

        addRole(id, student.getRole());
        return id;
    }

    @Override
    public void delete(Student student) {

    }

    @Override
    public int update(Student student) {
        return 0;
    }

    @Override
    public Student find(int id) {
        return null;
    }

    @Override
    public List<Student> findAll() {
        return null;
    }

    @Override
    public void addRole(int studentId, Role role) {
        jdbcTemplate.insert("INSERT INTO `user_group` (`user_id`, `group`) VALUES (?, ?);", studentId, role.getRole());
    }
}
