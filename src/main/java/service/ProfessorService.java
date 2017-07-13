package service;

import entities.Course;
import entities.Feedback;

import java.util.List;

/**
 * Interface of service to provide business options with professor.
 *
 * @author Yaroslav Baranov
 */
public interface ProfessorService {

    /**
     * Method to get all courses for professor
     *
     * @param professorLogin login of professor
     * @return list of courses
     */
    public List<Course> getCoursesForProfessor(String professorLogin);
}
