package org.composer.plugins.oracle;

import org.composer.plugins.PluginBase;

import java.util.Map;

public class OracleDatabasePlugin extends PluginBase {

    private static final String PROPERTY_PREFIX = "org.composer.plugins.oracle";

    @Override
    public void start() {
        Map<String, String> properties = filterProperties(PROPERTY_PREFIX);
        if(properties!=null && !properties.isEmpty())
        {

        }
    }

    @Override
    public void stop() {
        super.stop();
    }
}
