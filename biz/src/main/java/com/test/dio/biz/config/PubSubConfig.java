package com.test.dio.biz.config;

import com.test.dio.biz.listener.MessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.Executors;

@Configuration
public class PubSubConfig {

    @Autowired
    private Environment env;

    @Bean
    public MessageListenerAdapter messageListenerAdapter(MessageReceiver messageReceiver) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(messageReceiver);
        adapter.setSerializer(new StringRedisSerializer());
        return adapter;
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(LettuceConnectionFactory redisConnectionFactory,
                                                        MessageListenerAdapter messageListenerAdapter) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(env.getProperty("redis.topic")));
        container.setTaskExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        return container;
    }
}
