package service.impl;

import entities.Course;
import entities.Professor;
import persistence.dao.CourseDao;
import persistence.dao.ProfessorDao;
import persistence.dao.factory.DaoFactory;
import service.ProfessorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Realization of ProfessorService interface.
 *
 * @see service.ProfessorService
 * @author Yaroslav Baranov
 */
public class ProfessorServiceImpl implements ProfessorService {

    private CourseDao courseDao;

    private ProfessorDao professorDao;

    public ProfessorServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public ProfessorServiceImpl(DaoFactory daoFactory) {
        courseDao = daoFactory.getCourseDao();
        professorDao = daoFactory.getProfessorDao();
    }

    @Override
    public Professor getProfessorByLogin(String login) {
        Optional<Professor> professor = professorDao.findByLogin(login);

        if(!professor.isPresent()) {
            return null;
        }

        return professor.get();
    }

    @Override
    public List<Course> getCoursesForProfessor(String professorLogin) {
        List<Course> courses = courseDao.findAll();
        return courses.stream().filter(course -> course.getProfessor().getLogin().equals(professorLogin))
                .collect(Collectors.toList());
    }
}
