package persistence.dao.factory;

import entities.Professor;
import entities.Student;
import persistence.ConnectionManager;
import persistence.dao.CourseDao;
import persistence.dao.FeedbackDao;
import persistence.dao.LocationDao;
import persistence.dao.UserDao;
import persistence.dao.impl.CourseJdbcDao;
import persistence.dao.impl.FeedbackJdbcDao;
import persistence.dao.impl.LocationJdbcDao;
import persistence.dao.impl.UserJdbcDao;

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
    public CourseDao getCourseDao() {
        return new CourseJdbcDao(connectionManager);
    }

    @Override
    public FeedbackDao getFeedbackDao() {
        return new FeedbackJdbcDao(connectionManager);
    }

    @Override
    public LocationDao getLocationDao() {
        return new LocationJdbcDao(connectionManager);
    }
}
