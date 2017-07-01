package service;

import entities.Course;
import entities.Feedback;
import entities.Student;
import entities.User;
import persistence.dao.UserDao;
import service.util.CourseSearchParameters;

import java.util.List;


public interface CourseService {

    public Course getCourse(int id);

    public List<Course> getCourses();

    public void addNewCourse(Course course);

    public void deleteCourse(int id);

    public void deleteCourse(Course course);

    public void updateCourse(Course course);

    public List<Course> getCoursesForProfessor(String professorLogin);

    public List<Course> getCoursesForStudent(String login);

    public List<Course> getCoursesForStudentWithSearch(String login, CourseSearchParameters parameters);

    public List<Feedback> getFeedbacksByCourseId(int id);

    public void registerStudent(Course course, User user);

    public void unregisterUser(Course course, User user);

    public List<String> getDistinctCourseLocations();

    public double getMaxPriceOfCourse();
}

