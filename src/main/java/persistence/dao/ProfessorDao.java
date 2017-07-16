package persistence.dao;

import entities.Professor;

import java.util.Optional;

/**
 * Created by Ярослав on 16.07.2017.
 */
public interface ProfessorDao extends AbstractDao<Professor, Integer> {

    public Optional<Professor> findByLogin(String login);
}
