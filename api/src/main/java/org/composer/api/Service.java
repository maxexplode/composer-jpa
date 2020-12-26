package org.composer.api;

import org.composer.common.ServiceType;

public interface Service {
    ServiceType getType();

    default Object service() throws Exception {
        return null;
    }
}
