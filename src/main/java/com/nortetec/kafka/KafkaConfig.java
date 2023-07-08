package com.nortetec.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@ComponentScan(basePackageClasses = KafkaConfig.class)
@PropertySource("classpath:mensageria.properties")
public class KafkaConfig implements KafkaListenerConfigurer {
    @Value("${mensageria.kafka.host}")
    private String host;

    @Value("${mensageria.kafka.consumer.key-deserializer}")
    private String consumerKey;

    @Value("${mensageria.kafka.consumer.value-deserializer}")
    private String consumerValue;

    @Value("${mensageria.kafka.producer.key-serializer}")
    private String producerKey;

    @Value("${mensageria.kafka.producer.value-serializer}")
    private String producerValue;

    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerKey);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerValue);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,  producerKey);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerValue);
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerProps));
    }
}
