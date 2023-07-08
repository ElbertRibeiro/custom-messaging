package com.nortetec.kafka;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({KafkaConfig.class})
public @interface EnableCustomKafka {
}
