package com.ssafy.chat.config;

import com.ssafy.chat.dto.RecruitDto;
import com.ssafy.chat.entity.Chat;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVER;
    private final String GROUP_ID = UUID.randomUUID().toString();

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, Chat> consumerChatFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(Chat.class)); // Deserializer 클래스 형식을 지정
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Chat> kafkaChatListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Chat> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerChatFactory());
        factory.getContainerProperties().setPollTimeout(500); // 폴링 주기를 설정
        return factory;
    }

    @Bean
    public ConsumerFactory<String, RecruitDto> consumerRecruitFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(RecruitDto.class)); // Deserializer 클래스 형식을 지정
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RecruitDto> kafkaRecruitListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RecruitDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerRecruitFactory());
        factory.getContainerProperties().setPollTimeout(500); // 폴링 주기를 설정
        return factory;
    }

}