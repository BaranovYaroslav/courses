package service;

import entities.Course;
import entities.Student;
import entities.User;
import service.util.CourseSearchParameters;

import java.util.List;

/**
 * Interface of service to provide business options with student.
 *
 * @author Yaroslav Baranov
 */
public interface StudentService {

    /**
     * Method to get student by login.
     *
     * @param login login of user
     * @return found user
     */
    public Student getStudentByLogin(String login);

    /**
     * Method to give all courses of student.
     *
     * @param login login of student to give courses
     * @return list of courses
     */
    public List<Course> getCoursesForStudent(String login);

    /**
     * Method to give student all courses for registration.
     *
     * @param user user to give courses
     * @return list of courses
     */
    public List<Course> getCoursesForRegistration(Student student);

    /**
     * Method to give student all courses for registration according search parameters.
     *
     * @param login login of student to give courses
     * @param parameters search parameter
     * @see service.util.CourseSearchParameters
     * @return list of courses
     */
    public List<Course> getCoursesForRegistrationWithSearch(String login, CourseSearchParameters parameters);

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
}
