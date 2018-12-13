package com.test.dio.base.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:datasource-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
public class RedisConfig {

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean(destroyMethod = "shutdown")
    public RedisClient redisClient(ClientResources resources) {
        RedisURI redisURI = RedisURI.builder()
                .withHost(env.getProperty("redis.host"))
                .withPort(env.getProperty("redis.port", Integer.class))
                .withPassword(env.getProperty("redis.auth"))
                .build();
        return RedisClient.create(resources, redisURI);
    }

    @Bean(destroyMethod = "close")
    public StatefulRedisConnection statefulRedisConnection(RedisClient client) {
        return client.connect();
    }

    @Bean
    public RedisCommands redisCommands(StatefulRedisConnection connection) {
        return connection.sync();
    }

    @Bean
    public RedisAsyncCommands redisAsyncCommands(StatefulRedisConnection connection) {
        return connection.async();
    }
}
