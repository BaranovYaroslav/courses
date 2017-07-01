package persistence.dao;

import entities.Course;
import entities.Student;
import entities.User;

import java.util.List;

/**
 * Created by Ярослав on 11.04.2017.
 */
public interface CourseDao extends AbstractDao<Course> {

    public void delete(int id);

    public void registerStudent(Course course, User user);

    public void unregisterStudent(Course course, User user);
}
