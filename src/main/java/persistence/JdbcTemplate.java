package persistence;

import constants.LoggerMessage;
import org.apache.log4j.Logger;
import persistence.exeptions.RuntimeSqlException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            LOGGER.error(LoggerMessage.ON_SAVE_TO_DB_EXCEPTION_MESSAGE + e);
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
            LOGGER.error(LoggerMessage.ON_UPDATE_DATA_IN_DB_EXCEPTION_MESSAGE_ + e);
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
            LOGGER.error(LoggerMessage.ON_CANT_CREATE_PREPARED_STATEMENT_MESSAGE + query, e);
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
            LOGGER.error(LoggerMessage.ON_RESULT_SET_EXCEPTION_MESSAGE + e);
            throw new RuntimeSqlException(e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error(LoggerMessage.ON_CANT_CLOSE_RESULT_SET_MESSAGE, e);
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
     * Method that execute given sql file.
     *
     * @param file ti be executed
     * @return result of executing
     */
    public boolean executeSqlFile(File file) {
        try {
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                List<String> queries = Arrays.asList(content.split(";"))
                        .stream()
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());

                Connection connection = connectionManager.getConnection();

                try {
                    for (String query : queries) {
                        try (Statement stmt = connection.createStatement()) {
                            stmt.execute(query.trim());
                        }
                    }
                } finally {
                    closeConnection(connection);
                }

                return true;
            }
        } catch (IOException e) {
            LOGGER.warn(LoggerMessage.ON_SQL_SCRIPT_NOT_FOUND_MESSAGE);
            return false;
        }catch (SQLException e) {
            LOGGER.trace(LoggerMessage.ON_EXECUTION_SQL_SCRIPT_EXCEPTION_MESSAGE + e);
            return false;
        }

        return false;
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
            LOGGER.error(LoggerMessage.ON_CANT_CLOSE_CONNECTION_MESSAGE + e);
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
