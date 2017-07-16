package persistence;

import org.apache.log4j.Logger;
import persistence.dao.UserDao;
import persistence.exeptions.RuntimeSqlException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class that provide template for base operation with database.
 *
 * @author Yaroslav Baranov
 */
public class JdbcTemplate {

    private static Logger LOGGER = Logger.getLogger(JdbcTemplate.class);

    private ConnectionManager connectionManager;

    public JdbcTemplate() {}

    public JdbcTemplate(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Method that insert row in database
     *
     * @param query sql query to be executed
     * @param parameters parameters of query
     * @return index of database row that was inserted
     */
    public int insert(String query, Object... parameters) {
        Connection connection = connectionManager.getConnection();

        if(connection == null) {
            return -1;
        }

        try {
            System.out.println(1111);
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            System.out.println(111111111);
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
            throw new RuntimeSqlException(e);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Method that update row in database
     *
     * @param query sql query to be executed
     * @param parameters parameters of query
     * @return index of database row that was updated
     */
    public int update(String query, Object... parameters) {
        Connection connection = connectionManager.getConnection();

        if(connection == null) {
            return -1;
        }

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot execute update query", e);
            throw new RuntimeSqlException(e);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Method that query data from database according query and parameters and map
     * this data to certain entity.
     *
     * @param query sql query to be executed
     * @param fn function to be applied to result set
     * @param parameters parameters of query
     */
    public void query(String query, ResultSetFunction fn, Object... parameters) {
        Connection connection = connectionManager.getConnection();

        if(connection == null) {
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                stmt.setObject(i + 1, parameters[i]);
            }

            ResultSet rs = stmt.executeQuery();
            withRs(rs, fn);
        } catch (SQLException e) {
            LOGGER.error("Error creating prepared statement. Query: " + query, e);
            throw new RuntimeSqlException(e);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Method that apply result set function to given result set.
     *
     * @param rs result set from database
     * @param fn function to be applied to result set
     */
    public void withRs(ResultSet rs, ResultSetFunction fn) {
        try {
            fn.apply(rs);
        } catch (Exception e) {
            LOGGER.error("ResultSetFunctions has thrown an exception", e);
            throw new RuntimeSqlException(e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error("Cannot tryClose ResultSet", e);
            }
        }
    }

    /**
     * Method that query one entity from database according query, query parameters and producer.
     *
     * @param query sql query to be executed
     * @param producer function to be applied to for mapping
     * @param parameters parameters of query
     */
    public <R> Optional<R> queryObject(String query, EntityExtractor<R> producer, Object... parameters) {
        Object[] r = new Object[]{null};
        query(query, (rs) -> {
            if (rs.next()) {
                r[0] = producer.apply(rs);
            }
        }, parameters);

        if(r[0] == null) {
            return Optional.empty();
        }
        return Optional.of((R) r[0]);
    }

    /**
     * Method that query list entities from database according query, query parameters and producer.
     *
     * @param query sql query to be executed
     * @param producer function to be applied to for mapping
     * @param parameters parameters of query
     */
    public <R> List<R> queryObjects(String query, EntityExtractor<R> producer, Object... parameters) {
        List<R> entities = new ArrayList<>();

        query(query, rs -> {
            while (rs.next()) {
                entities.add(producer.apply(rs));
            }
        }, parameters);

        return entities;
    }

    /**
     * Method that close database connection
     *
     * @param connection connection to close
     */
    private void closeConnection(Connection connection) {
        try {
            if(connection != null) {
                connection.close();
            }
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
