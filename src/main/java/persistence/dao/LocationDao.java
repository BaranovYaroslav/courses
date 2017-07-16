package persistence.dao;

import entities.Location;


/**
 * Created by Ярослав on 16.07.2017.
 */
public interface LocationDao extends AbstractDao<Location, Integer> {

    public void delete(Integer id);
}
