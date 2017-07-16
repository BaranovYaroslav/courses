package persistence.dao;

import entities.Professor;

import java.util.Optional;

/**
 * Interface for professor data access object.
 *
 * @author Yaroslav Baranov
 */
public interface ProfessorDao extends AbstractDao<Professor, Integer> {

    /**
     * Method that extract professor from database.
     *
     * @param login login of professor
     * @return Optional of found professor
     */
    public Optional<Professor> findByLogin(String login);
}
