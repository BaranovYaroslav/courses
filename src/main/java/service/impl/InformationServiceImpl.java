package service.impl;

import constants.ApplicationConstants;
import entities.User;
import persistence.dao.CourseDao;
import persistence.dao.UserDao;
import persistence.dao.factory.DaoFactory;
import security.UserRole;
import service.InformationService;

/**
 * Realization of InformationService interface.
 *
 * @see service.InformationService
 * @author Yaroslav Baranov
 */
public class InformationServiceImpl implements InformationService{

    private long courseNumber = ApplicationConstants.DEFAULT_NON_INITIALIZED_VALUE_OF_INTEGER;

    private long studentNumber = ApplicationConstants.DEFAULT_NON_INITIALIZED_VALUE_OF_INTEGER;

    private long professorNumber = ApplicationConstants.DEFAULT_NON_INITIALIZED_VALUE_OF_INTEGER;

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
        studentNumber = userDao.findAll().stream()
                               .filter(u -> u.getRole().getRole().equals(UserRole.STUDENT)).count();
        professorNumber = userDao.findAll().stream()
                               .filter(p -> p.getRole().getRole().equals(UserRole.PROFESSOR)).count();
    }

    private long getTimeFromLastAccess() {
        return System.currentTimeMillis() - lastAccessTime;
    }


    @Override
    public long getCoursesNumber() {
        checkTimeout();
        return courseNumber;
    }

    @Override
    public long getProfessorsNumber() {
        checkTimeout();
        return professorNumber;
    }

    @Override
    public long getStudentsNumber() {
        checkTimeout();
        return studentNumber;
    }
}
