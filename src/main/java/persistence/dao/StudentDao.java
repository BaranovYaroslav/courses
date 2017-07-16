package persistence.dao;

import entities.Student;

import javax.swing.text.Style;
import java.util.Optional;

/**
 * Interface for student data access object.
 *
 * @author Yaroslav Baranov
 */
public interface StudentDao extends AbstractDao<Student, Integer> {

    /**
     * Method that extract student from database.
     *
     * @param login login of student
     * @return Optional of found student
     */
    public Optional<Student> findByLogin(String login);
}
