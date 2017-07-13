package service;

import entities.Course;
import entities.Student;
import entities.User;
import service.util.CourseSearchParameters;

import java.util.List;

/**
 * Created by Ярослав on 18.04.2017.
 */
public interface StudentService {

    public List<Course> getCoursesForRegistration(User user);

    public List<Course> getCoursesForStudent(String login);

    public List<Course> getCoursesForStudentWithSearch(String login, CourseSearchParameters parameters);

    public void registerStudent(Course course, User user);

    public void unregisterStudent(Course course, User user);
}
