package org.composer.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.composer.api.Service;
import org.composer.api.ServiceRegistry;
import org.composer.api.introspect.IntrospectPhase;
import org.composer.common.AppConstants;
import org.composer.common.ComposerRecord;
import org.composer.common.IntrospectContext;
import org.composer.common.ProjectConfig;
import org.composer.impl.generator.Generator;

import java.sql.*;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Function;

public class ComposerJPA {

    public static Function<Class<? extends Service>, ServiceLoader> loaderDelegate = ServiceLoader::load;

    static {
        System.setProperty(AppConstants.COMPOSER_HOME, "C:\\Projects\\composer-jpa\\");
        init();
    }

    static void init() {
        //Log4j

        LoggerContext context = LoggerContext.getContext(false);

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
                                List<ComposerRecord.SqlType> sqlTypes = mapper.getSqlTypes(metaData, configuration);
                                IntrospectContext context = new IntrospectContext(tables, sqlTypes, ProjectConfig.builder()
                                        .name("Composer Demo")
                                        .packageName("org.composer.generate.demo")
                                        .outPath("C:\\Projects\\composer-jpa\\gen")
                                        .extension(".java")
                                        .escapingDelimiters(new char[]{'_'})
                                        .build());
                                List<IntrospectPhase> service = ServiceRegistry.<IntrospectPhase>findService(ServiceRegistry.ServiceType.PHASER);
                                service.forEach(introspectPhase -> {
                                    introspectPhase.handle(context);
                                });
                                Generator generator = new Generator(context);
                                generator.init();
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
