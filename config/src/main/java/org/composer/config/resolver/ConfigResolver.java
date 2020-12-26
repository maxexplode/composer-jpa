package org.composer.config.resolver;


import org.composer.api.Service;
import org.composer.common.ServiceType;
import org.composer.config.Config;

public class ConfigResolver implements Service {

    @Override
    public void service() throws Exception {
        resolveConfiguration();
    }

    @Override
    public ServiceType getType()
    {
        return ServiceType.CONFIG;
    }

    public Config resolveConfiguration() throws Exception {
        return null;
    }
}
