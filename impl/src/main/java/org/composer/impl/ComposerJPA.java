package org.composer.impl;

import org.composer.api.Service;
import org.composer.api.ServiceRegistry;
import org.composer.api.introspect.IntrospectPhase;
import org.composer.common.ComposerRecord;
import org.composer.common.IntrospectContext;

import java.sql.*;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Function;

public class ComposerJPA {

    public static Function<Class<? extends Service>, ServiceLoader> loaderDelegate = (a) ->
            ServiceLoader.load(a.getClass());

    static {
        init();
    }

    static void init() {
        ServiceLoader<Service> services = ServiceLoader.load(Service.class);
        services.forEach(service -> {
            ServiceLoader<? extends Service> load = ServiceLoader.load(service.getClass());
            if (load.findFirst().isPresent()) {
                load.stream().forEach(provider -> {
                    ServiceRegistry.registerService(provider.get().getType(), provider.get());
                });
            } else {
                ServiceRegistry.registerService(service.getType(), service);
            }
        });
    }

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
                                introspectTables(tables);
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

    private static void introspectTables(List<ComposerRecord.Table> tables) {
        IntrospectContext context = new IntrospectContext(tables);
        List<IntrospectPhase> service = ServiceRegistry.<IntrospectPhase>findService(ServiceRegistry.ServiceType.PHASER);
        service.forEach(introspectPhase -> {
            introspectPhase.handle(context);
        });
    }

}
