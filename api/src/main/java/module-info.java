import org.composer.api.Service;
import org.composer.api.introspect.IntrospectPhase;

module api {
    requires common;
    exports org.composer.api.introspect;
    exports org.composer.api;
    provides Service with IntrospectPhase;

}