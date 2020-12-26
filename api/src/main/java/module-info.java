import org.composer.api.Service;
import org.composer.api.introspect.IntrospectPhase;

module api {
    requires common;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    exports org.composer.api.introspect;
    exports org.composer.api;
    provides Service with IntrospectPhase;

}