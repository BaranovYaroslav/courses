package persistence.dao.factory;

import entities.Professor;
import entities.Student;
import entities.User;
import persistence.dao.*;

/**
 * Interface of factory to retrieve objects of DAO.
 *
 * @author Yaroslav Baranov
 */
public interface DaoFactory {
    public UserDao getUserDao();

    public ProfessorDao getProfessorDao();

    public StudentDao getStudentDao();

    public CourseDao getCourseDao();

    public FeedbackDao getFeedbackDao();

    public LocationDao getLocationDao();
}
