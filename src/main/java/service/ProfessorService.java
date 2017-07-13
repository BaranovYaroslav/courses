package service;

import entities.Course;
import entities.Feedback;

import java.util.List;

/**
 * Created by Ярослав on 13.07.2017.
 */
public interface ProfessorService {

    public List<Course> getCoursesForProfessor(String professorLogin);
}
