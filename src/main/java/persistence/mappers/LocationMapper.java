package persistence.mappers;

import constants.DatabaseColumn;
import entities.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that extract location from database result set.
 *
 * @author Yaroslav Baranov
 */
public class LocationMapper {

    public static Location map(ResultSet resultSet) throws SQLException{
        Location.Builder builder = Location.newBuilder();

        builder.setId(resultSet.getInt(DatabaseColumn.ID))
               .setCity(resultSet.getString(DatabaseColumn.CITY))
               .setAddress(resultSet.getString(DatabaseColumn.ADDRESS))
               .setXCoordinate(resultSet.getDouble(DatabaseColumn.X_COORDINATE))
               .setYCoordinate(resultSet.getDouble(DatabaseColumn.Y_COORDINATE));

        return builder.build();
    }
}
