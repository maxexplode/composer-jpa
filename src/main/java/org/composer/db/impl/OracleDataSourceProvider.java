package org.composer.db.impl;

import org.composer.db.DataSourceProvider;

import javax.sql.DataSource;
import java.util.Properties;

public class OracleDataSourceProvider implements DataSourceProvider.Provider {

    @Override
    public String propertyPrefix() {
        return "oracle.";
    }

    @Override
    public String url() {
        return "jdbc:oracle:thin:@{0}:{1}:{2}";
    }

    @Override
    public Properties properties(Properties properties) {
        Properties oraProperties = new Properties();
        oraProperties.put("username", properties.get(propertyPrefix().concat("username")));
        oraProperties.put("password", properties.get(propertyPrefix().concat("password")));
        return oraProperties;
    }

    @Override
    public DataSource createDataSource() {

        return null;
    }
}
