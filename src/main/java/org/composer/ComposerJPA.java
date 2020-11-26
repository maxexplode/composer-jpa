package org.composer;

import java.sql.*;

import java.util.List;

public class ComposerJPA {

    public static void main(String[] args) {
        try {

            System.out.println("Welcome To ComposerJPA!");

            ComposerConfiguration configuration = new ComposerConfiguration();

            configuration.setSchemaPatterns("C##ORACLECOMPOSER");

            String url = "jdbc:oracle:thin:@//157.230.39.211:51521/xe";
            Connection connection = DriverManager.getConnection(url, "C##ORACLECOMPOSER", "ORACLECOMPOSER");
            assert connection != null;
            DatabaseMetaData metaData = connection.getMetaData();
            assert metaData != null;

            ResultSetMapper mapper = new ResultSetMapper();

            System.out.println("Got MetaData !");

            System.out.println(
                    "Product : " + metaData.getDatabaseProductName() + "\n"
                            + "Version : " + metaData.getDatabaseProductVersion() + "\n"
            );
            List<ComposerRecord.Scheme> schemes = mapper.getSchemes(metaData);
            if (!schemes.isEmpty()) {
                schemes.stream().filter(scheme -> scheme.scheme().equals(configuration.getSchemaPatterns())).findAny().ifPresent(
                        scheme -> {
                            try {
                                System.out.println("matched scheme : " + scheme.scheme());
                                System.out.println("matched catalog : " + scheme.catalog());
                                configuration.setCatalog(scheme.catalog());
                                List<ComposerRecord.Table> tables = mapper.getTables(metaData, configuration);
                                System.out.println(tables);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
