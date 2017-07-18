package service.impl;

import entities.*;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.dao.CourseDao;
import persistence.dao.FeedbackDao;
import persistence.dao.StudentDao;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import persistence.transaction.Transaction;
import service.FeedbackService;
import service.StudentService;
import service.util.CourseSearchParameters;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Realization of StudentService interface.
 *
 * @see service.StudentService
 * @author Yaroslav Baranov
 */
public class StudentServiceImpl implements StudentService {

    private static Logger LOGGER = Logger.getLogger(StudentService.class);

    private CourseDao courseDao;

    private StudentDao studentDao;

    private FeedbackDao feedbackDao;

    private ConnectionManager connectionManager;

    public StudentServiceImpl(CourseDao courseDao, StudentDao studentDao, FeedbackDao feedbackDao, ConnectionManager connectionManager) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.feedbackDao = feedbackDao;
        this.connectionManager = connectionManager;
    }

    public StudentServiceImpl(DaoFactory daoFactory, ConnectionManager connectionManager) {
        courseDao = daoFactory.getCourseDao();
        feedbackDao = daoFactory.getFeedbackDao();
        studentDao = daoFactory.getStudentDao();
        this.connectionManager = connectionManager;
    }

    @Override
    public Student getStudentByLogin(String login) {
        Optional<Student> student = studentDao.findByLogin(login);

        if(!student.isPresent()) {
            return null;
        }

        return student.get();
    }

    @Override
    public List<Course> getCoursesForRegistration(Student student) {
        List<Course> courses = courseDao.findAll();
        LocalDate now = LocalDate.now();
        return courses.stream().filter(course -> !course.getStudents().contains(student))
                               .filter(course -> now.isBefore(course.getStartDate()))
                               .collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesForStudent(String login) {
        List<Course> courses = courseDao.findAll();
        Student student = studentDao.findByLogin(login).get();
        return courses.stream().filter(course -> course.getStudents().contains(student)).collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesForRegistrationWithSearch(String login, CourseSearchParameters parameters) {
        Student student = studentDao.findByLogin(login).get();
        List<Course> courses = courseDao.findAll();

        if(parameters.getType().length() != 0) {
            courses = courses.stream().filter(course -> course.getType().getType().equals(parameters.getType()))
                    .collect(Collectors.toList());
        }
        if(parameters.getLocation().length() != 0) {
            courses = courses.stream().filter(course -> course.getLocation().getCity().equals(parameters.getLocation()))
                    .collect(Collectors.toList());
        }
        if(parameters.isOnlyFree()) {
            courses = courses.stream().filter(Course::getIsFree).collect(Collectors.toList());
        } else {
            courses = courses.stream().filter(course -> course.getPrice() >= parameters.getMinPrice() &&
                    course.getPrice() <= parameters.getMaxPrice())
                    .collect(Collectors.toList());
        }

        return courses.stream().filter(course -> !course.getStudents().contains(student)).collect(Collectors.toList());
    }

    @Override
    public synchronized void registerStudent(Course course, Student student) {
        if(course.getStudents().size() < course.getNumberOfStudents()) {
            Transaction.of(connectionManager, () -> {
                courseDao.registerStudent(course, student);
                Feedback feedback = FeedbackService.createEmptyFeedback(course, student);
                feedbackDao.add(feedback);}
            );
        }
    }

    @Override
    public void unregisterStudent(Course course, Student student) {
        courseDao.unregisterStudent(course, student);
    }
}
