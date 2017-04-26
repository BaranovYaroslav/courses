package persistence.dao;

import entities.Course;
import entities.Student;
import entities.User;

/**
 * Created by Ярослав on 11.04.2017.
 */
public interface CourseDao extends AbstractDao<Course> {

    public void delete(int id);

    public void registerStudent(Course course, User user);

    public void unregisterStudent(Course course, User user);

}
