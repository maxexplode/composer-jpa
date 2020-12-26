import org.composer.api.Service;
import org.composer.config.resolver.ConfigResolver;
import org.composer.config.resolver.FileConfigResolver;

module config {
    requires common;
    requires api;
    requires lombok;
    requires org.yaml.snakeyaml;
    requires org.apache.logging.log4j;
    exports org.composer.config.resolver;
    exports org.composer.config to org.yaml .snakeyaml, impl;
    provides Service with ConfigResolver;
    provides ConfigResolver with FileConfigResolver;
}