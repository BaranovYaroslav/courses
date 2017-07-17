package persistence;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.log4j.Logger;
import persistence.dao.UserDao;
import persistence.transaction.DataSourceTxProxy;
import util.ResourceLoader;

import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Holder of access to database via connections.
 *
 * @author Yaroslav Baranov
 */
public class ConnectionManager {

    private static Logger LOGGER = Logger.getLogger(UserDao.class);

    private DataSource dataSource;

    public ConnectionManager() {}

    public ConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Method to obtain connection to database.
     *
     * @return connection to database
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Cannot create connection to the database", e);
        }

        return null;
    }

    public void clean() {
        if (dataSource instanceof DataSourceTxProxy) {
            DataSourceTxProxy txDs = (DataSourceTxProxy) dataSource;
            txDs.clean();
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Method to obtain instance of ConnectionManager according settings from properties file.
     *
     * @param path path of properties file.
     * @return instance of ConnectionManager
     */
    public static ConnectionManager fromProperties(String path) {
        MysqlDataSource dataSource = new MysqlDataSource();

        Properties properties = ResourceLoader.fromFile(path);

        dataSource.setURL(properties.getProperty("url"));
        dataSource.setUser(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));

        DataSource txDataSource = new DataSourceTxProxy(dataSource);

        return new ConnectionManager(txDataSource);
    }

    /**
     * Method to obtain instance of ConnectionManager from jndi.
     *
     * @param name jndi name.
     * @return instance of ConnectionManager
     */
    public static ConnectionManager fromJndi(String name) {
        try {
            Context initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:comp/env");
            DataSource dataSource = (DataSource) envContext.lookup(name);
            DataSource txDataSource = new DataSourceTxProxy(dataSource);

            return new ConnectionManager(txDataSource);
        } catch (NamingException e) {
            LOGGER.error("Can't create jndi context");
            return null;
        }
    }

    /**
     * Method to obtain instance of ConnectionManager from DataSource.
     *
     * @param dataSource DataSource object.
     * @return instance of ConnectionManager
     */

    public static ConnectionManager fromDataSource(DataSource dataSource) {
        return new ConnectionManager(new DataSourceTxProxy(dataSource));
    }
}
