package persistence.dao.factory;

import entities.Professor;
import entities.Student;
import entities.User;
import persistence.dao.CourseDao;
import persistence.dao.FeedbackDao;
import persistence.dao.UserDao;

/**
 * Interface of factory to retrieve objects of DAO.
 *
 * @author Yaroslav Baranov
 */
public interface DaoFactory {
    public UserDao getUserDao();

    public CourseDao getCourseDao();

    public FeedbackDao getFeedbackDao();
}
