package persistence.dao;

import entities.Course;
import entities.Student;
import entities.User;

import java.util.List;

/**
 * Interface for course data access object.
 *
 * @author Yaroslav Baranov
 */
public interface CourseDao extends AbstractDao<Course, Integer> {

    /**
     * Method that delete entity from database.
     *
     * @param id id of entity to be deleted
     */
    public void delete(int id);

    /**
     * Method that register given user to given course.
     *
     * @param course course for registration
     * @param user user to be register
     */
    public void registerStudent(Course course, User user);

    /**
     * Method that unregister given user from given course.
     *
     * @param course course for unregister
     * @param user user to be unregister
     */
    public void unregisterStudent(Course course, User user);

    /**
     * Method that unregister all users from course.
     *
     * @param courseId id of course
     */
    public void unregisterUsersFromCourse(int courseId);

}
