package persistence.dao.factory;

import persistence.ConnectionManager;
import persistence.dao.CourseDao;
import persistence.dao.FeedbackDao;
import persistence.dao.UserDao;
import persistence.dao.impl.CourseJdbcDao;
import persistence.dao.impl.FeedbackJdbcDao;
import persistence.dao.impl.UserJdbcDao;

/**
 * Created by Ярослав on 11.04.2017.
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
}
