package com.nortetec.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableRabbit
@ComponentScan(basePackageClasses = RabbitMQConfig.class)
@PropertySource("classpath:mensageria.properties")
public class RabbitMQConfig {

    @Value("${mensageria.rabbitmq.host}")
    private String host;
    @Value("${mensageria.rabbitmq.port}")
    private String port;
    @Value("${mensageria.rabbitmq.usuario}")
    private String usuario;
    @Value("${mensageria.rabbitmq.senha}")
    private String senha;
    @Value("${mensageria.rabbitmq.projetoTag}")
    private String aplicacao;


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(Integer.parseInt(port));
        connectionFactory.setUsername(usuario);
        connectionFactory.setPassword(senha);
        return connectionFactory;
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConsumerTagStrategy(q -> aplicacao);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
