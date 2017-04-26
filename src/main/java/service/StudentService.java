package service;

import entities.Course;
import entities.Student;
import entities.User;

import java.util.List;

/**
 * Created by Ярослав on 18.04.2017.
 */
public interface StudentService {

    public List<Course> getCoursesForRegistration(User user);
}
