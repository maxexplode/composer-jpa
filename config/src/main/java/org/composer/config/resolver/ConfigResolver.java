package org.composer.config.resolver;


import org.composer.api.Service;
import org.composer.common.ServiceType;
import org.composer.config.FileConfig;

public class ConfigResolver implements Service {

    @Override
    public Object service() throws Exception {
        return resolveConfiguration();
    }

    @Override
    public ServiceType getType()
    {
        return ServiceType.CONFIG;
    }

    public FileConfig resolveConfiguration() throws Exception {
        return null;
    }
}
