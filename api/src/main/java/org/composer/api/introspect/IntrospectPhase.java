package org.composer.api.introspect;

import org.composer.common.IntrospectContext;
import org.composer.api.Service;
import org.composer.api.ServiceRegistry;

public class IntrospectPhase implements Service {

    public void handle(IntrospectContext context)
    {

    }

    @Override
    public ServiceRegistry.ServiceType getType() {
        return ServiceRegistry.ServiceType.PHASER;
    }
}
