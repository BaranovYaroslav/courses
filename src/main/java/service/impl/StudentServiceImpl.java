package service.impl;

import entities.Course;
import entities.Feedback;
import entities.User;
import persistence.dao.CourseDao;
import persistence.dao.FeedbackDao;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import service.FeedbackService;
import service.StudentService;
import service.util.CourseSearchParameters;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ярослав on 18.04.2017.
 */
public class StudentServiceImpl implements StudentService {

    private CourseDao courseDao;

    private UserDao userDao;

    private FeedbackDao feedbackDao;

    public StudentServiceImpl(CourseDao courseDao, UserDao userDao, FeedbackDao feedbackDao) {
        this.courseDao = courseDao;
        this.userDao = userDao;
        this.feedbackDao = feedbackDao;
    }

    public StudentServiceImpl(DaoFactory daoFactory) {
        courseDao = daoFactory.getCourseDao();
        feedbackDao = daoFactory.getFeedbackDao();
        userDao = daoFactory.getUserDao();
    }

    @Override
    public List<Course> getCoursesForRegistration(User user) {
        List<Course> courses = courseDao.findAll();
        return courses.stream().filter(course -> !course.getStudents().contains(user))
                               .collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesForStudent(String login) {
        List<Course> courses = courseDao.findAll();
        User user = userDao.getUser(login).get();
        return courses.stream().filter(course -> course.getStudents().contains(user)).collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesForStudentWithSearch(String login, CourseSearchParameters parameters) {
        User user = userDao.getUser(login).get();
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

        return courses;
    }

    @Override
    public synchronized void registerStudent(Course course, User user) {
        if(course.getStudents().size() < course.getNumberOfStudents()) {
            courseDao.registerStudent(course, user);
            Feedback feedback = FeedbackService.createEmptyFeedback(course, user);
            feedbackDao.add(feedback);
        }
    }

    @Override
    public void unregisterStudent(Course course, User user) {
        courseDao.unregisterStudent(course, user);
    }
}
