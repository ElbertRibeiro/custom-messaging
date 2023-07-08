package com.nortetec.rabbitmq;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({RabbitMQConfig.class})
public @interface EnableCustomRabbitMQ {
}
