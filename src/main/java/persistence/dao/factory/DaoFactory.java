package persistence.dao.factory;

import persistence.dao.CourseDao;
import persistence.dao.FeedbackDao;
import persistence.dao.UserDao;

/**
 * Created by Ярослав on 11.04.2017.
 */
public interface DaoFactory {
    public UserDao getUserDao();

    public CourseDao getCourseDao();

    public FeedbackDao getFeedbackDao();
}
