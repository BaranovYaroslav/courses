package persistence;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.log4j.Logger;
import persistence.dao.UserDao;
import util.PropertiesLoader;

import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class ConnectionManager {

    private static Logger LOGGER = Logger.getLogger(UserDao.class);

    private DataSource dataSource;

    public ConnectionManager() {}

    public ConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ConnectionManager fromProperties(String path) {
        MysqlDataSource dataSource = new MysqlDataSource();

        Properties properties = PropertiesLoader.fromFile(path);

        dataSource.setURL(properties.getProperty("url"));
        dataSource.setUser(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));

        return new ConnectionManager(dataSource);
    }

    public static ConnectionManager fromJndi(String name) {
        try {
            Context initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:comp/env");
            DataSource dataSource = (DataSource) envContext.lookup(name);
            return new ConnectionManager(dataSource);
        } catch (NamingException e) {
            LOGGER.error("Can't create jndi context");
            return null;
        }
    }
}
