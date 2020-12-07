package org.composer.impl.phase;

import org.composer.api.introspect.IntrospectContext;
import org.composer.impl.service.Service;
import org.composer.impl.service.ServiceRegistry;

public interface IntrospectPhase extends Service {
    void handle(IntrospectContext context);

    @Override
    default ServiceRegistry.ServiceType getType() {
        return ServiceRegistry.ServiceType.PHASER;
    }
}
