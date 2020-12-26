package org.composer.config.resolver;


import org.composer.api.Service;
import org.composer.api.ServiceRegistry;
import org.composer.config.ComposerConfig;
import org.composer.config.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigResolver implements Service {

    @Override
    public void service() throws Exception {
        resolveConfiguration();
    }

    @Override
    public ServiceRegistry.ServiceType getType()
    {
        return ServiceRegistry.ServiceType.CONFIG;
    }

    public Config resolveConfiguration() throws Exception {
        return null;
    }
}
