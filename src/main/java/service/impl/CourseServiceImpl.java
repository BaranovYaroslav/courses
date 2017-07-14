package service.impl;

import entities.Course;
import entities.Feedback;
import entities.User;
import org.apache.log4j.Logger;
import persistence.ConnectionManager;
import persistence.dao.CourseDao;
import persistence.dao.FeedbackDao;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import persistence.transaction.Transaction;
import service.CourseService;
import service.util.CourseSearchParameters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Realization of CourseService interface.
 *
 * @see service.CourseService
 * @author Yaroslav Baranov
 */
public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao;

    private FeedbackDao feedbackDao;

    private ConnectionManager connectionManager;

    public CourseServiceImpl(CourseDao courseDao, FeedbackDao feedbackDao, ConnectionManager connectionManager) {
        this.courseDao = courseDao;
        this.feedbackDao = feedbackDao;
        this.connectionManager = connectionManager;
    }

    public CourseServiceImpl(DaoFactory daoFactory, ConnectionManager connectionManager) {
        courseDao = daoFactory.getCourseDao();
        feedbackDao = daoFactory.getFeedbackDao();
        this.connectionManager = connectionManager;
    }

    @Override
    public Course getCourse(int id) {
        return courseDao.find(id).get();
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
        Transaction.of(connectionManager, () -> {
            feedbackDao.deleteFeedbacksByCourseId(id);
            courseDao.unregisterUsersFromCourse(id);
            courseDao.delete(id);
        });
    }

    @Override
    public void deleteCourse(Course course) {

    }

    @Override
    public void updateCourse(Course course) {
        courseDao.update(course);
    }

    @Override
    public List<String> getDistinctCourseLocations() {
        List<Course> courses = courseDao.findAll();
        List<String> cities = new ArrayList<>();

        courses.forEach(course -> {
            String city = course.getLocation().getCity();
            if(!cities.contains(city)) {
                cities.add(city);
            }
        });

        return cities;
    }

    @Override
    public double getMaxPriceOfCourse() {
        List<Course> courses = courseDao.findAll();
        double result = 0;


        if(courses.size() > 0) {
            courses.sort(new Comparator<Course>() {
                @Override
                public int compare(Course o1, Course o2) {
                    return (int)(1000 * o2.getPrice() - 1000 * o1.getPrice());
                }
            });
            result = courses.get(0).getPrice();
        }

        return result;
    }
}
