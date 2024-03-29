package com.ssafy.fcm.config;

import com.ssafy.chat.dto.FcmDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class KafkaConfig {

    private final String GROUP_ID = UUID.randomUUID().toString();
    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVER;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }

    //    @Bean
//    public ConsumerFactory<String, Map<String, Object>> consumerFactory() {
//        JsonDeserializer<Map<String, Object>> jsonDeserializer = new JsonDeserializer<>();
//        jsonDeserializer.addTrustedPackages("*"); // 모든 패키지 신뢰 설정
//
//        return new DefaultKafkaConsumerFactory<>(
//                consumerConfigs(),
//                new StringDeserializer(),
//                new ErrorHandlingDeserializer<>(jsonDeserializer)); // Deserializer 클래스 형식을 지정
//    }
    @Bean
    public ConsumerFactory<String, FcmDto> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(FcmDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FcmDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FcmDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setPollTimeout(500); // 폴링 주기를 설정
        return factory;
    }

}