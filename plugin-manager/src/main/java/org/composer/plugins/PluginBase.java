package org.composer.plugins;

import java.util.Map;
import java.util.stream.Collectors;

public class PluginBase implements Plugin {
    protected Context context;

    @Override
    public void initialize(Context context) {
        this.context = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    protected Map<String, String> filterProperties(String prefix) {
        if (context != null && context.getProperties() != null) {
            return context.getProperties().entrySet().stream().filter(prop -> prop.getKey().toString().startsWith(prefix))
                    .collect(Collectors.toMap(e -> (String) e.getKey(), e -> (String) e.getValue()));
        }
        return null;
    }
}
