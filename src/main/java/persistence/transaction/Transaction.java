package persistence.transaction;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.log4j.LogManager;
import org.hibernate.annotations.common.util.impl.Log;
import persistence.ConnectionManager;
import org.apache.log4j.Logger;
import persistence.exeptions.RuntimeSqlException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database transaction realization.
 *
 * @author Yaroslav Baranov.
 */
@FunctionalInterface
public interface Transaction {

    static Logger LOGGER = LogManager.getLogger(Transaction.class);

    void span();

    /**
     * Transaction wrapper for DataSourceTxProxy.
     *
     * @param connectionManager is the DataSource holder object
     * @param transaction is a method object or lambda that contains the database operations
     * @param isolationLevel is the level of database transaction isolation level
     */
    static void of(ConnectionManager connectionManager, Transaction transaction, int isolationLevel) {
        Connection conn = connectionManager.getConnection();

        boolean autoCommit;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            if(conn.getTransactionIsolation() != isolationLevel) {
                conn.setTransactionIsolation(isolationLevel);
            }

            transaction.span();

            conn.setAutoCommit(autoCommit);
        } catch (RuntimeSqlException | SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOGGER.error("Cannot rollback transaction");
            }
        } catch (Exception e) {
            LOGGER.error("Operations failed");
            throw e;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("Failed to close connection");
            }
        }
    }

    static void of(ConnectionManager connectionManager, Transaction transaction) {
        of(connectionManager, transaction, Connection.TRANSACTION_READ_COMMITTED);
    }

}
