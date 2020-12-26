import org.composer.api.introspect.IntrospectPhase;
import org.composer.api.Service;
import org.composer.config.resolver.ConfigResolver;
import org.composer.impl.phase.InitializationPhase;
import org.composer.impl.phase.IdentifierPhase;

module impl {
    uses Service;
    uses IntrospectPhase;
    uses ConfigResolver;
    requires common;
    requires api;
    requires org.apache.commons.text;
    requires config;
    requires java.sql;
    requires velocity.engine.core;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    provides IntrospectPhase with InitializationPhase, IdentifierPhase;
}