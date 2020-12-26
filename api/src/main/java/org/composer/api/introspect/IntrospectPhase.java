package org.composer.api.introspect;

import org.composer.common.IntrospectContext;
import org.composer.api.Service;
import org.composer.common.ServiceType;

public class IntrospectPhase implements Service {

    public void handle(IntrospectContext context)
    {

    }

    @Override
    public ServiceType getType() {
        return ServiceType.PHASER;
    }
}
