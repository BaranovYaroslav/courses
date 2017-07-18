package persistence;

import org.h2.jdbcx.JdbcDataSource;
import util.ResourceLoader;

/**
 * Util class to provide ConnectionManager that holds H2 database connections.
 *
 * @author Yaroslv Baranov
 */
public class H2Db {

    public static ConnectionManager init(String initScript) {
        ConnectionManager connectionManager = ConnectionManager.fromDataSource(getH2DS());
        JdbcTemplate jdbcTemplate = new JdbcTemplate(connectionManager);
        jdbcTemplate.executeSqlFile(ResourceLoader.getFile(initScript));

        return connectionManager;
    }

    /**
     * Method to obtain datasource for H2 database.
     *
     * @return object of DataSource
     */
    private static JdbcDataSource getH2DS() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl("jdbc:h2:mem:test;Mode=MYSQL;DB_CLOSE_DELAY=-1");
        ds.setUser("user");
        ds.setPassword("user");
        return ds;
    }
}
