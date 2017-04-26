package service.impl;

import entities.Course;
import entities.Student;
import entities.User;
import persistence.ConnectionManager;
import persistence.dao.CourseDao;
import persistence.dao.StudentDao;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import persistence.dao.impl.CourseJdbcDao;
import persistence.dao.impl.StudentJdbcDao;
import persistence.dao.impl.UserJdbcDao;
import service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class StudentServiceImpl implements StudentService {

    private UserDao userDao;

    private CourseDao courseDao;

    public StudentServiceImpl(DaoFactory daoFactory) {
        userDao = daoFactory.getUserDao();
        courseDao = daoFactory.getCourseDao();
    }

    @Override
    public List<Course> getCoursesForRegistration(User user) {
        List<Course> courses = courseDao.findAll();
        return courses.stream().filter(course -> !course.getStudents().contains(user))
                               .collect(Collectors.toList());
    }
}
