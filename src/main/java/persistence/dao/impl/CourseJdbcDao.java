package persistence.dao.impl;

import entities.Course;
import entities.Feedback;
import entities.Student;
import entities.User;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.JdbcTemplate;
import persistence.dao.CourseDao;
import persistence.dao.FeedbackDao;
import persistence.dao.UserDao;
import persistence.mappers.CourseMapper;
import persistence.mappers.StudentIndexMapper;
import persistence.mappers.UserMapper;

import java.util.ArrayList;
import java.util.List;

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
        return jdbcTemplate.insert("INSERT INTO `course` (`name`, `description`, `start_date`, `end_date`, `professor_id`, " +
                        "`city`, `address`, `x`, `y`, `students_number`, `price`, `is_free`) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                course.getName(), course.getDescription(), course.getStartDate(), course.getEndDate(),
                course.getProfessor().getId(), course.getLocation().getCity(), course.getLocation().getAddress(),
                course.getLocation().getXCoordinate(), course.getLocation().getYCoordinate(),
                course.getNumberOfStudents(), course.getPrice(), course.getIsFree());
    }

    @Override
    public void delete(Course course) {
        jdbcTemplate.update("DELETE FROM `course` WHERE id=?;", course.getId());
    }

    @Override
    public void delete(int id) {
        delete(find(id));
    }

    @Override
    public int update(Course course) {
        return jdbcTemplate.update("UPDATE `course` SET `name`=?, `description`=?, `start_date`=?, `end_date`=?, " +
                        "`professor_id`=?, `city`=?, `address`=?, `x`=?, `y`=?, `students_number`=?, " +
                        "`price` = ?, `is_free`=? WHERE id=?;",
                course.getName(), course.getDescription(), course.getStartDate(), course.getEndDate(),
                course.getProfessor().getId(), course.getLocation().getCity(), course.getLocation().getAddress(),
                course.getLocation().getXCoordinate(), course.getLocation().getYCoordinate(),
                course.getNumberOfStudents(), course.getPrice(), course.getIsFree(), course.getId());
    }

    @Override
    public Course find(int id) {
        Course course = jdbcTemplate.queryObject("SELECT * FROM `course` WHERE `id`=?;", CourseMapper::map, id);

        if(course != null) {
            setProfessor(course);
            setStudents(course);
        }
        return course;
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = jdbcTemplate.queryObjects("SELECT * FROM `course`", CourseMapper::map);

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
        jdbcTemplate.insert("INSERT INTO `student_course` (`student_id`, `course_id`) VALUES (?, ?);",
                            user.getId(), course.getId());
    }

    @Override
    public void unregisterStudent(Course course, User user) {
        jdbcTemplate.update("DELETE FROM `student_course` WHERE `student_id`=? AND `course_id`=?;",
                            user.getId(), course.getId());
    }

    private void setProfessor(Course course) {
        course.setProfessor(userDao.find(course.getProfessor().getId()));
    }

    private void setStudents(Course course) {
        List <User> students = new ArrayList<>();
        List<Integer> studentIndexes = jdbcTemplate.queryObjects("SELECT * FROM `student_course` WHERE course_id=?;",
                StudentIndexMapper::map, course.getId());


        studentIndexes.forEach(studentIndex -> students.add(jdbcTemplate.queryObject("SELECT * FROM `user` WHERE id=?;",
                UserMapper::map, studentIndex)));
        course.setStudents(students);
    }
}
