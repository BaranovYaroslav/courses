package service;

import entities.Course;
import entities.Feedback;
import entities.Professor;
import entities.User;

import java.util.List;

/**
 * Interface of service to provide business options with professor.
 *
 * @author Yaroslav Baranov
 */
public interface ProfessorService {

    /**
     * Method to get professor by login.
     *
     * @param login login of user
     * @return found user
     */
    public Professor getProfessorByLogin(String login);

    /**
     * Method to get all courses for professor
     *
     * @param professorLogin login of professor
     * @return list of courses
     */
    public List<Course> getCoursesForProfessor(String professorLogin);
}
