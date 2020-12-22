package org.composer.config;

import org.composer.common.stereotype.config.Config;
import org.composer.common.stereotype.config.ConfigProperty;

@Config(value = "template")
public class TemplateConfig {
    @ConfigProperty("gen")
    private String generator;
    @ConfigProperty("location")
    private String location;
}
