package org.composer.impl.db;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataSourceFactory {
    private static DataSourceFactory dataSourceFactory;
    private static final String DRIVER_LOCATION = "user.db_drivers";
    private static String location;

    private DataSourceFactory() throws Exception {
        String dbDrivers = System.getProperty(DRIVER_LOCATION);
        if (dbDrivers == null || dbDrivers.length() == 0) {
            throw new Exception("Cannot find property user.db_drivers");
        }
        if (!Files.exists(Paths.get(dbDrivers))) {
            throw new FileNotFoundException("Could not find : " + dbDrivers);
        }
        location = dbDrivers;

    }

    public static DataSourceFactory getInstance() throws Exception {
        if (dataSourceFactory == null) {
            dataSourceFactory = new DataSourceFactory();
        }
        return dataSourceFactory;
    }

    public enum DB {
        ORACLE,
    }

    public DataSource createDataSource(DB db) {
        return null;
    }
}
