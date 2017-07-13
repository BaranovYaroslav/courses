package service;

import entities.Course;
import entities.Feedback;
import entities.Student;
import entities.User;
import persistence.dao.UserDao;
import service.util.CourseSearchParameters;

import java.util.List;

/**
 * Interface of service to provide business options with course.
 *
 * @author Yaroslav Baranov
 */
public interface CourseService {

    /**
     * Method to get course by id.
     *
     * @param id id of course
     * @return found course
     */
    public Course getCourse(int id);

    /**
     * Method to get all courses.
     *
     * @return list of courses
     */
    public List<Course> getCourses();

    /**
     * Method to add new course to database.
     *
     * @param course course to be added
     */
    public void addNewCourse(Course course);

    /**
     * Method to delete course from database.
     *
     * @param id id of course to be deleted
     */
    public void deleteCourse(int id);

    /**
     * Method to delete course from database.
     *
     * @param course course to be deleted
     */
    public void deleteCourse(Course course);

    /**
     * Method to update course in database.
     *
     * @param course course to be updated
     */
    public void updateCourse(Course course);

    /**
     * Method to get all locations from courses.
     *
     * @return list of locations
     */
    public List<String> getDistinctCourseLocations();

    /**
     * Method to get maximal price from courses.
     *
     * @return maximal price from courses
     */
    public double getMaxPriceOfCourse();
}

