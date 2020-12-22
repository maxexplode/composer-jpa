package org.composer.config;

import org.composer.common.stereotype.config.Config;
import org.composer.common.stereotype.config.ConfigProperty;

@Config("composer")
public class ComposerConfig {
    @ConfigProperty("template-config")
    private TemplateConfig templateConfig;
}
