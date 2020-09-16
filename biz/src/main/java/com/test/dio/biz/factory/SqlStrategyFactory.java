package com.test.dio.biz.factory;

import com.test.dio.biz.strategy.sql.SqlStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SqlStrategyFactory {

    private static final String DEFAULT_DB = "MYSQL";

    @Autowired
    private Map<String, SqlStrategy> sqlStrategyMap = new ConcurrentHashMap<>(16);

    public SqlStrategy getStrategy(String dbType) {
        SqlStrategy sqlStrategy = sqlStrategyMap.get(dbType);

        if (Objects.isNull(sqlStrategy)) {
            return sqlStrategyMap.get(DEFAULT_DB);
        }
        return sqlStrategy;
    }
}
