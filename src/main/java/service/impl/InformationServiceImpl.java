package service.impl;

import application.ApplicationConstants;
import persistence.dao.CourseDao;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import service.CourseService;
import service.InformationService;
import service.ServiceLoader;
import service.UserService;

/**
 * Created by Ярослав on 26.06.2017.
 */
public class InformationServiceImpl implements InformationService{

    private int courseNumber = ApplicationConstants.DEFAULT_NON_INITIALIZED_VALUE_OF_INTEGER;

    private int studentNumber = ApplicationConstants.DEFAULT_NON_INITIALIZED_VALUE_OF_INTEGER;

    private int professorNumber = ApplicationConstants.DEFAULT_NON_INITIALIZED_VALUE_OF_INTEGER;

    private long lastAccessTime = ApplicationConstants.DEFAULT_NULL_VALUE_FOR_NUMBERS;

    private CourseDao courseDao;

    private UserDao userDao;

    public InformationServiceImpl(DaoFactory daoFactory) {
        courseDao = daoFactory.getCourseDao();
        userDao = daoFactory.getUserDao();
    }

    private void checkTimeout() {
        if(getTimeFromLastAccess() > ApplicationConstants.DEFAULT_TIMEOUT_TIME_FOR_DB) {
            lastAccessTime = System.currentTimeMillis();
            refreshInformation();
        }
    }

    private void refreshInformation() {
        courseNumber = courseDao.findAll().size();
        studentNumber = userDao.findAll().size();
        professorNumber = userDao.findAll().size();
    }

    private long getTimeFromLastAccess() {
        return System.currentTimeMillis() - lastAccessTime;
    }


    @Override
    public int getCoursesNumber() {
        checkTimeout();
        return courseNumber;
    }

    @Override
    public int getStudentsNumber() {
        checkTimeout();
        return studentNumber;
    }

    @Override
    public int getProfessorsNumber() {
        checkTimeout();
        return professorNumber;
    }
}
