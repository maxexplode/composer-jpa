package org.composer.api;

import org.composer.common.ServiceType;

public interface Service {
    ServiceType getType();

    default void service() throws Exception {

    }
}
