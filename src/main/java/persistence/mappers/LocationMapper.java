package persistence.mappers;

import entities.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ярослав on 16.07.2017.
 */
public class LocationMapper {

    public static Location map(ResultSet resultSet) throws SQLException{
        Location.Builder builder = Location.newBuilder();

        builder.setId(resultSet.getInt("id"))
               .setCity(resultSet.getString("city"))
               .setAddress(resultSet.getString("address"))
               .setXCoordinate(resultSet.getDouble("x"))
               .setYCoordinate(resultSet.getDouble("y"));

        return builder.build();
    }
}
