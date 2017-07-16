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
     * Method that register given student to given course.
     *
     * @param course course for registration
     * @param student student to be register
     */
    public void registerStudent(Course course, Student student);

    /**
     * Method that unregister given student from given course.
     *
     * @param course course for unregister
     * @param student student to be unregister
     */
    public void unregisterStudent(Course course, Student student);

    /**
     * Method that unregister all users from course.
     *
     * @param courseId id of course
     */
    public void unregisterUsersFromCourse(int courseId);

}
