package org.composer.api;

public interface Service {
    ServiceRegistry.ServiceType getType();

    default void service() throws Exception {

    }
}
