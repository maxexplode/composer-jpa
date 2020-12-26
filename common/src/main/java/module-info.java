module common {
    requires lombok;
    requires ojdbc10;
    requires java.sql;
    requires org.apache.logging.log4j;
    exports org.composer.common;
    exports org.composer.common.model;
}