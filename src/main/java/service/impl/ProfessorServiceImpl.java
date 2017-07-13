package service.impl;

import entities.Course;
import entities.Professor;
import persistence.dao.CourseDao;
import persistence.dao.factory.DaoFactory;
import service.ProfessorService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ярослав on 13.07.2017.
 */
public class ProfessorServiceImpl implements ProfessorService {

    private CourseDao courseDao;

    public ProfessorServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public ProfessorServiceImpl(DaoFactory daoFactory) {
        courseDao = daoFactory.getCourseDao();
    }

    @Override
    public List<Course> getCoursesForProfessor(String professorLogin) {
        List<Course> courses = courseDao.findAll();
        return courses.stream().filter(course -> course.getProfessor().getLogin().equals(professorLogin))
                .collect(Collectors.toList());
    }
}
