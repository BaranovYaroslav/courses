package persistence.dao;

import entities.Student;

import javax.swing.text.Style;
import java.util.Optional;

/**
 * Created by Ярослав on 16.07.2017.
 */
public interface StudentDao extends AbstractDao<Student, Integer> {

    public Optional<Student> findByLogin(String login);
}
