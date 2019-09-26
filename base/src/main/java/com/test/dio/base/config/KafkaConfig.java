package com.test.dio.base.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Autowired
    private Environment env;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.bootstrap.servers"));
        return new KafkaAdmin(configs);
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.bootstrap.servers"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("kafka.consumer.group-id"));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public Map<String, Object> produceConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.bootstrap.servers"));
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<Object, Object> kafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ProducerFactory<Object, Object> kafkaProducerFactory() {
        return new DefaultKafkaProducerFactory<>(produceConfigs());
    }

    @Bean
    public ProducerListener<Object, Object> kafkaProducerListener() {
        return new LoggingProducerListener<>();
    }

    @Bean
    public KafkaTemplate<?, ?> kafkaTemplate(ProducerFactory<Object, Object> producerFactory,
                                             ProducerListener<Object, Object> producerListener) {
        KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setProducerListener(producerListener);
        return kafkaTemplate;
    }
}
