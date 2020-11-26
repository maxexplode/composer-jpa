package org.composer.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataSourceProvider {

    Map<DataSourceFactory.DB, Provider> providerCache = new HashMap<>();

    private DataSource createDataSource() throws SQLException {

        Provider provider = providerCache.get(DataSourceFactory.DB.ORACLE);
        Connection connection = DriverManager.getConnection(provider.url(), provider.properties(System.getProperties()));

        return null;
    }


    public interface Provider {
        String propertyPrefix();

        String url();

        Properties properties(Properties properties);

        DataSource createDataSource();
    }
}
