package service.impl;

import entities.Course;
import entities.Feedback;
import entities.User;
import org.apache.log4j.Logger;
import persistence.dao.CourseDao;
import persistence.dao.FeedbackDao;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import service.CourseService;
import service.util.CourseSearchParameters;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ярослав on 15.04.2017.
 */
public class CourseServiceImpl implements CourseService {

    private static Logger LOGGER = Logger.getLogger(CourseServiceImpl.class);

    private UserDao userDao;

    private CourseDao courseDao;

    private FeedbackDao feedbackDao;

    public CourseServiceImpl(DaoFactory daoFactory) {
        userDao = daoFactory.getUserDao();
        courseDao = daoFactory.getCourseDao();
        feedbackDao = daoFactory.getFeedbackDao();
    }

    @Override
    public Course getCourse(int id) {
        return courseDao.find(id);
    }

    @Override
    public List<Course> getCourses() {
        return courseDao.findAll();
    }

    @Override
    public void addNewCourse(Course course) {
        courseDao.add(course);
    }

    @Override
    public void deleteCourse(int id) {
        courseDao.delete(id);
    }

    @Override
    public void deleteCourse(Course course) {

    }

    @Override
    public void updateCourse(Course course) {
        courseDao.update(course);
    }

    @Override
    public List<Course> getCoursesForProfessor(String professorLogin) {
        List<Course> courses = courseDao.findAll();
        return courses.stream().filter(course -> course.getProfessor().getLogin().equals(professorLogin))
                               .collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesForStudent(String login) {
        List<Course> courses = courseDao.findAll();
        User user = userDao.getUser(login);
        return courses.stream().filter(course -> course.getStudents().contains(user)).collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesForStudentWithSearch(String login, CourseSearchParameters parameters) {
        User user = userDao.getUser(login);
        List<Course> courses = courseDao.findAll();
        courses = courses.stream().filter(course -> !course.getStudents().contains(user))
                                  .collect(Collectors.toList());

        if(parameters.getType().length() != 0) {
            courses = courses.stream().filter(course -> course.getType().equals(parameters.getType()))
                                      .collect(Collectors.toList());
        }
        if(parameters.getLocation().length() != 0) {
            courses = courses.stream().filter(course -> course.getLocation().equals(parameters.getLocation()))
                                      .collect(Collectors.toList());
        }
        if(parameters.isOnlyFree()) {
            courses = courses.stream().filter(Course::isFree).collect(Collectors.toList());
        } else {
            courses = courses.stream().filter(course -> course.getPrice() >= parameters.getMinPrice() &&
                                                        course.getPrice() <= parameters.getMaxPrice())
                                      .collect(Collectors.toList());
        }

        return courses;
    }

    @Override
    public List<Feedback> getFeedbacksByCourseId(int id) {
        return feedbackDao.getFeedbacksForCourse(id);
    }

    @Override
    public void registerStudent(Course course, User user) {
        courseDao.registerStudent(course, user);
        Feedback feedback = createEmptyFeedback(course, user);
        feedbackDao.add(feedback);
    }

    @Override
    public void unregisterUser(Course course, User user) {
        courseDao.unregisterStudent(course, user);
    }

    private Feedback createEmptyFeedback(Course course, User user) {
        Feedback.Builder builder = Feedback.newBuilder();

        builder.setComment("")
               .setScore(0)
               .setStudent(user)
               .setCourse(course);

        return builder.build();
    }
}
