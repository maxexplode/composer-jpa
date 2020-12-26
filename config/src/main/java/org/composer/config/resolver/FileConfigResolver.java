package org.composer.config.resolver;

import org.composer.common.AppConstants;
import org.composer.config.Config;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

public class FileConfigResolver extends ConfigResolver {

    public static final String FILE_CONFIG = "composer.yml";

    @Override
    public Config resolveConfiguration() throws IOException {
        String configLocation = System.getProperty(AppConstants.COMPOSER_HOME);
        if (configLocation != null) {
            String composerConfig = configLocation + AppConstants.CONFIGURATION + File.separator + FILE_CONFIG;
            File file = new File(composerConfig);
            if (file.exists() && file.canRead()) {
                return new Yaml(new Constructor(Config.class)).load(new FileInputStream(file));
            }
        }
        return new Config();
    }
}
