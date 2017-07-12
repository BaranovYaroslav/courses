package persistence;

import org.apache.log4j.Logger;
import persistence.dao.UserDao;
import persistence.exeptions.RuntimeSqlException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class JdbcTemplate {

    private static Logger LOGGER = Logger.getLogger(JdbcTemplate.class);

    private ConnectionManager connectionManager;

    public JdbcTemplate() {}

    public JdbcTemplate(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void startTransaction(int txIsolationLevel) {
        Connection txConnection = connectionManager.getConnection();

        try {
            txConnection.setAutoCommit(false);
            txConnection.setTransactionIsolation(txIsolationLevel);
        } catch (SQLException e) {
            LOGGER.error("Cannot start transaction. Your application may be data inconsistent", e);
            throw new RuntimeSqlException(e);
        } finally {
            closeConnection(txConnection);
        }
    }

    /**
     * calls {@link #startTransaction(int)} with txIsolationLevel set to
     * Connection.TRANSACTION_READ_COMMITTED
     */
    public void startTransaction() {
        startTransaction(Connection.TRANSACTION_READ_COMMITTED);
    }

    /**
     * Commits the changes made by the underlying connection
     *
     * Note, this method should be called only when the DataSource supports single thread
     * transaction spanning(e.g. DataSourceTxProxy)
     */
    public void commit() {
        Connection conn = connectionManager.getConnection();
        try {
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Cannot commit");
            throw new RuntimeSqlException(e);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Rollbacks the changes made by the underlying connection
     *
     * Note, this method should be called only when the DataSource supports single thread
     * transaction spanning(e.g. DataSourceTxProxy)
     */
    public void rollback() {
        Connection conn = connectionManager.getConnection();
        try {
            conn.rollback();
        } catch (SQLException e) {
            LOGGER.error("Cannot call rollback", e);
            throw new RuntimeSqlException(e);
        } finally {
            closeConnection(conn);
        }
    }

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
            LOGGER.error("Exception when trying save entity to DB: " + e);
            throw new RuntimeSqlException(e);
        } finally {
            closeConnection(connection);
        }
    }

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
            return -1;
        } finally {
            closeConnection(connection);
        }
    }

    public void query(String query, ResultSetFunction fn, Object... params) {
        Connection connection = connectionManager.getConnection();

        if(connection == null) {
            return;
        }

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

    public <R> Optional<R> queryObject(String query, EntityExtractor<R> producer, Object... params) {
        Object[] r = new Object[]{null};
        query(query, (rs) -> {
            if (rs.next()) {
                r[0] = producer.apply(rs);
            }
        }, params);

        if(r[0] == null) {
            return Optional.empty();
        }
        return Optional.of((R) r[0]);
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
