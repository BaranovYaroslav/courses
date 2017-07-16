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
import persistence.mappers.StudentMapper;
import persistence.mappers.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Realization of CourseDao interface.
 *
 * @see persistence.dao.CourseDao
 * @author Yaroslav Baranov
 */
public class CourseJdbcDao implements CourseDao {

    private static Logger LOGGER = Logger.getLogger(UserDao.class);

    private JdbcTemplate jdbcTemplate;

    public CourseJdbcDao(ConnectionManager connectionManager) {
        jdbcTemplate = new JdbcTemplate(connectionManager);
    }

    @Override
    public Integer add(Course course) {
        return jdbcTemplate.insert(Query.INSERT_COURSE_QUERY,
                course.getLocation().getCity(), course.getLocation().getAddress(),
                course.getLocation().getXCoordinate(), course.getLocation().getYCoordinate(),
                course.getName(), course.getDescription(), course.getStartDate(), course.getEndDate(),
                course.getProfessor().getId(), course.getNumberOfStudents(), course.getPrice(),
                course.getIsFree(), course.getType().getType());
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
    public Integer update(Course course) {
        return jdbcTemplate.update(Query.UPDATE_COURSE_QUERY,
                course.getName(), course.getDescription(), course.getStartDate(), course.getEndDate(),
                course.getProfessor().getId(), course.getNumberOfStudents(), course.getPrice(),
                course.getIsFree(), course.getType().getType(), course.getId());
    }

    @Override
    public Optional<Course> find(Integer id) {
        Optional<Course> course = jdbcTemplate.queryObject(Query.FIND_COURSE_QUERY, CourseMapper::map, id);

        if(course.isPresent()) {
            setStudents(course.get());
        }
        return course;
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = jdbcTemplate.queryObjects(Query.FIND_ALL_COURSES_QUERY, CourseMapper::map);

        if(courses != null){
            for(Course course: courses) {
                setStudents(course);
            }
        }

        return courses;
    }

    @Override
    public void registerStudent(Course course, Student user) {
        jdbcTemplate.insert(Query.REGISTER_STUDENT_QUERY, user.getId(), course.getId());
    }

    @Override
    public void unregisterStudent(Course course, Student student) {
        jdbcTemplate.update(Query.UNREGISTER_STUDENT_QUERY, student.getId(), course.getId());
    }

    @Override
    public void unregisterUsersFromCourse(int courseId) {
        jdbcTemplate.update(Query.UNREGISTER_ALL_STUDENTS_FROM_COURSE_QUERY, courseId);
    }

    private void setStudents(Course course) {
        List<Student> students = new ArrayList<>();
        students = jdbcTemplate.queryObjects(Query.GET_STUDENTS_FOR_COURSE_QUERY, StudentMapper::map, course.getId());
        course.setStudents(students);
    }
}
