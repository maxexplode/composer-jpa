package org.composer.config.resolver;

import org.composer.common.AppConstants;
import org.composer.config.ConfigUtil;
import org.composer.config.FileConfig;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

public class FileConfigResolver extends ConfigResolver {

    @Override
    public FileConfig resolveConfiguration() throws IOException {
        String configLocation = System.getProperty(AppConstants.COMPOSER_HOME);
        if (configLocation != null) {
            String composerConfig = ConfigUtil.composerConfig();
            File file = new File(composerConfig);
            if (file.exists() && file.canRead()) {
                return new Yaml(new Constructor(FileConfig.class)).load(new FileInputStream(file));
            }
        }
        return new FileConfig();
    }
}
