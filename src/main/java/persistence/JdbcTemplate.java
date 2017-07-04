package persistence;

import org.apache.log4j.Logger;
import persistence.dao.UserDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class JdbcTemplate {

    private static Logger LOGGER = Logger.getLogger(UserDao.class);

    private ConnectionManager connectionManager;

    public JdbcTemplate() {}

    public JdbcTemplate(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public int insert(String query, Object... parameters){
        Connection connection = connectionManager.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next()) {
                return rs.getInt(1);
            }

            return -1;
        } catch (SQLException e) {
            LOGGER.error("Exception when trying save entity to DB: " + e);
            return -1;
        } finally {
            closeConnection(connection);
        }
    }

    public int update(String query, Object... parameters) {
        Connection connection = connectionManager.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot execute update query", e);
            return -1;
        } finally {
            closeConnection(connection);
        }
    }

    public void query(String query, ResultSetFunction fn, Object... params) {
        Connection connection = connectionManager.getConnection();

        if(connection == null)
            return;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            withRs(rs, fn);
        } catch (SQLException e) {
            LOGGER.error("Error creating prepared statement. Query: " + query, e);
        } finally {
            closeConnection(connection);
        }
    }
    public void withRs(ResultSet rs, ResultSetFunction fn) {
        try {
            fn.apply(rs);
        } catch (Exception e) {
            LOGGER.error("ResultSetFunctions has thrown an exception", e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error("Cannot tryClose ResultSet", e);
            }
        }
    }

    public <R> R queryObject(String query, EntityExtractor<R> producer, Object... params) {
        Object[] r = new Object[]{null};
        query(query, (rs) -> {
            if (rs.next()) {
                r[0] = producer.apply(rs);
            }
        }, params);

        return (R) r[0];
    }

    public <R> List<R> queryObjects(String query, EntityExtractor<R> producer, Object... params) {
        List<R> entities = new ArrayList<>();

        query(query, rs -> {
            while (rs.next()) {
                entities.add(producer.apply(rs));
            }
        }, params);

        return entities;
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Can't close connection: " + e);
        }
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @FunctionalInterface
    public interface EntityExtractor<R> {
        R apply(ResultSet resultSet) throws SQLException;
    }

    @FunctionalInterface
    public interface ResultSetFunction {
        void apply(ResultSet resultSet) throws SQLException;
    }

}
