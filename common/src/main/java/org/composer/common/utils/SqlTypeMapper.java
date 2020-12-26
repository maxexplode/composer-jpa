package org.composer.common.utils;

import oracle.jdbc.OracleType;
import org.composer.common.ComposerRecord;
import org.composer.common.Database;

import java.sql.SQLType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlTypeMapper {
    private final ComposerRecord.SqlType sqlType;
    private final Database database;

    public enum JavaType {
        STRING(java.lang.String.class),
        CHARACTER(java.lang.Character.class),
        BIG_DECIMAL(java.math.BigDecimal.class),
        BIG_INTEGER(java.math.BigInteger.class),
        BOOLEAN(java.lang.Boolean.class),
        LONG(java.lang.Long.class),
        BYTE(java.lang.Byte.class),
        INTEGER(java.lang.Integer.class),
        FLOAT(java.lang.Float.class),
        DOUBLE(java.lang.Double.class),
        SQL_DATE(java.sql.Date.class),
        UTIL_DATE(java.util.Date.class),
        SQL_TIMESTAMP(java.sql.Timestamp.class),
        SQL_TIME(java.sql.Time.class),
        SHORT(java.lang.Short.class),
        SQL_BLOB(java.sql.Blob.class),
        SQL_CLOB(java.sql.Clob.class),
        SQL_STRUCT(java.sql.Struct.class),
        SQL_REF(java.sql.Ref.class),
        SQL_ARRAY(java.sql.Array.class);

        private Class<?> type;

        JavaType(Class<?> aClass) {
            this.type = aClass;
        }

        public String typeName() {
            return type.getName();
        }

        public String simpleTypeName() {
            return type.getSimpleName();
        }
    }

    public SqlTypeMapper(ComposerRecord.SqlType sqlType, Database database) {
        this.sqlType = sqlType;
        this.database = database;
    }

    private final Map<SQLType, List<JavaType>> ORACLE_TYPES = new HashMap<>() {{
        put(OracleType.CHAR, List.of(JavaType.STRING));
        put(OracleType.VARCHAR2, List.of(JavaType.STRING));
        put(OracleType.LONG, List.of(JavaType.STRING));
        put(OracleType.NUMBER, List.of(
                JavaType.BIG_DECIMAL,
                JavaType.BIG_INTEGER,
                JavaType.BOOLEAN,
                JavaType.SHORT,
                JavaType.BYTE,
                JavaType.LONG,
                JavaType.DOUBLE,
                JavaType.FLOAT));
        put(OracleType.RAW, List.of(JavaType.BYTE));
        put(OracleType.LONG_RAW, List.of(JavaType.BYTE));
        put(OracleType.DATE, List.of(
                JavaType.SQL_TIME,
                JavaType.SQL_DATE,
                JavaType.SQL_TIMESTAMP));
        put(OracleType.BLOB, List.of(
                JavaType.SQL_BLOB,
                JavaType.SQL_CLOB));
        ;
    }};
}
