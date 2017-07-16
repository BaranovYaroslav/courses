package persistence.dao.impl;

import entities.Location;
import persistence.ConnectionManager;
import persistence.JdbcTemplate;
import persistence.Query;
import persistence.dao.LocationDao;
import persistence.mappers.LocationMapper;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ярослав on 16.07.2017.
 */
public class LocationJdbcDao implements LocationDao{

    private JdbcTemplate jdbcTemplate;

    public LocationJdbcDao(ConnectionManager connectionManager) {
        this.jdbcTemplate = new JdbcTemplate(connectionManager);
    }


    @Override
    public Integer add(Location location) {
        return jdbcTemplate.insert(Query.INSERT_LOCATION_QUERY, location.getCity(), location.getAddress(),
                                   location.getYCoordinate(), location.getYCoordinate());
    }

    @Override
    public void delete(Location location) {
        jdbcTemplate.update(Query.DELETE_LOCATION_QUERY, location.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(Query.DELETE_LOCATION_QUERY, id);
    }

    @Override
    public Integer update(Location location) {
        return jdbcTemplate.update(Query.UPDATE_LOCATION_QUERY, location.getCity(), location.getAddress(),
                                   location.getXCoordinate(), location.getYCoordinate(), location.getId());
    }

    @Override
    public Optional<Location> find(Integer id) {
        return jdbcTemplate.queryObject(Query.FIND_LOCATION_QUERY, LocationMapper::map, id);
    }

    @Override
    public List<Location> findAll() {
        return jdbcTemplate.queryObjects(Query.FIND_ALL_LOCATIONS_QUERY, LocationMapper::map);
    }
}
