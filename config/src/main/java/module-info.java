import org.composer.api.Service;
import org.composer.config.resolver.ConfigResolver;
import org.composer.config.resolver.FileConfigResolver;

module config {
    requires common;
    requires api;
    exports org.composer.config.resolver;
    provides Service with ConfigResolver;
    provides ConfigResolver with FileConfigResolver;
}