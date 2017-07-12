package persistence.dao.impl;

import entities.*;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.JdbcTemplate;
import persistence.Query;
import persistence.dao.CourseDao;
import persistence.dao.UserDao;
import persistence.mappers.CourseMapper;
import persistence.mappers.StudentIndexMapper;
import persistence.mappers.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class CourseJdbcDao implements CourseDao {

    private static Logger LOGGER = Logger.getLogger(UserDao.class);

    private ConnectionManager connectionManager;

    private JdbcTemplate jdbcTemplate;

    private UserDao userDao;

    public CourseJdbcDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        jdbcTemplate = new JdbcTemplate(connectionManager);
        userDao = new UserJdbcDao(connectionManager);
    }

    @Override
    public int add(Course course) {
        return jdbcTemplate.insert(Query.INSERT_COURSE_QUERY,
                course.getName(), course.getDescription(), course.getStartDate(), course.getEndDate(),
                course.getProfessor().getId(), course.getLocation().getCity(), course.getLocation().getAddress(),
                course.getLocation().getXCoordinate(), course.getLocation().getYCoordinate(),
                course.getNumberOfStudents(), course.getPrice(), course.getIsFree(), course.getType().getType());
    }

    @Override
    public void delete(Course course) {
        jdbcTemplate.update(Query.DELETE_COURSE_QUERY, course.getId());
    }

    @Override
    public void delete(int id) {
        delete(find(id).get());
    }

    @Override
    public int update(Course course) {
        return jdbcTemplate.update(Query.UPDATE_COURSE_QUERY,
                course.getName(), course.getDescription(), course.getStartDate(), course.getEndDate(),
                course.getProfessor().getId(), course.getLocation().getCity(), course.getLocation().getAddress(),
                course.getLocation().getXCoordinate(), course.getLocation().getYCoordinate(),
                course.getNumberOfStudents(), course.getPrice(), course.getIsFree(),
                course.getType().getType(), course.getId());
    }

    @Override
    public Optional<Course> find(int id) {
        Optional<Course> course = Optional.of(jdbcTemplate.queryObject(Query.FIND_COURSE_QUERY, CourseMapper::map, id));

        if(course.isPresent()) {
            setProfessor(course.get());
            setStudents(course.get());
        }
        return course;
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = jdbcTemplate.queryObjects(Query.FIND_ALL_COURSES_QUERY, CourseMapper::map);

        if(courses != null){
            for(Course course: courses) {
                setProfessor(course);
                setStudents(course);
            }
        }

        return courses;
    }

    @Override
    public void registerStudent(Course course, User user) {
        jdbcTemplate.insert(Query.REGISTER_STUDENT_QUERY, user.getId(), course.getId());
    }

    @Override
    public void unregisterStudent(Course course, User user) {
        jdbcTemplate.update(Query.UNREGISTER_STUDENT_QUERY, user.getId(), course.getId());
    }

    private void setProfessor(Course course) {
        course.setProfessor(userDao.find(course.getProfessor().getId()).get());
    }

    private void setStudents(Course course) {
        List<User> students = new ArrayList<>();
        students = jdbcTemplate.queryObjects(Query.GET_STUDENTS_FOR_COURSE_QUERY, UserMapper::map);
        course.setStudents(students);
    }
}
