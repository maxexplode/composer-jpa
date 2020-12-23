package org.composer.config.resolver;


import org.composer.api.Service;
import org.composer.api.ServiceRegistry;


public class ConfigResolver implements Service {

    @Override
    public ServiceRegistry.ServiceType getType()
    {
        return ServiceRegistry.ServiceType.CONFIG;
    }

    void resolveConfiguration()
    {

    }
}
