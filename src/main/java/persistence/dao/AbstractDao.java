package persistence.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interface of abstract data access object.
 *
 * @author Yaroslav Baranov
 */
public interface AbstractDao<T> {

    /**
     * Method that add entity to database.
     *
     * @param entity object of entity to be added
     * @return index of database row that contains inserted entity
     */
    public int add(T entity);

    /**
     * Method that delete entity from database.
     *
     * @param entity object of entity to be deleted
     */
    public void delete(T entity);

    /**
     * Method that update entity in database.
     *
     * @param entity object of entity to be updated
     * @return index of database row that contains updated entity
     */
    public int update(T entity);

    /**
     * Method that extract entity from database.
     *
     * @param id id of entity to be found
     * @return Optional of found entity
     */
    public Optional<T> find(int id);

    /**
     * Method that extract all entities from database.
     *
     * @return list of entities
     */
    public List<T> findAll();
}
