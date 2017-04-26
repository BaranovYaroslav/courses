package persistence.dao;

import entities.Role;
import entities.Student;

/**
 * Created by Ярослав on 21.04.2017.
 */
public interface StudentDao extends AbstractDao<Student> {

    public void addRole(int studentId, Role role);

}
