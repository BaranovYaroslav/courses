package persistence.dao;

import entities.Location;

/**
 * Interface for location data access object.
 *
 * @author Yaroslav Baranov
 */
public interface LocationDao extends AbstractDao<Location, Integer> {

    /**
     * Method that delete location from database.
     *
     * @param id id of location to be deleted
     */
    public void delete(Integer id);
}
