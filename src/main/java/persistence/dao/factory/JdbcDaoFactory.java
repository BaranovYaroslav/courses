package persistence.dao.factory;

import entities.Professor;
import entities.Student;
import persistence.ConnectionManager;
import persistence.dao.*;
import persistence.dao.impl.*;

/**
 * Realization of DaoFactory interface.
 *
 * @see persistence.dao.factory.DaoFactory
 * @author Yaroslav Baranov
 */
public class JdbcDaoFactory implements DaoFactory {

    private ConnectionManager connectionManager;

    public JdbcDaoFactory(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public UserDao getUserDao() {
        return new UserJdbcDao(connectionManager);
    }

    @Override
    public ProfessorDao getProfessorDao() {
        return new ProfessorJdbcDao(connectionManager, this.getUserDao());
    }

    @Override
    public StudentDao getStudentDao() {
        return new StudentJdbcDao(connectionManager, this.getUserDao());
    }

    @Override
    public CourseDao getCourseDao() {
        return new CourseJdbcDao(connectionManager);
    }

    @Override
    public FeedbackDao getFeedbackDao() {
        return new FeedbackJdbcDao(connectionManager, this.getStudentDao(), this.getCourseDao());
    }

    @Override
    public LocationDao getLocationDao() {
        return new LocationJdbcDao(connectionManager);
    }
}
