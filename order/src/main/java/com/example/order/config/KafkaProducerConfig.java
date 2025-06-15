package com.example.order.config;

import com.example.order.model.dto.OrderCreatedDto;
import com.example.order.model.dto.PaymentStatusDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> getProducerConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, 10);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return configProps;
    }

    @Bean
    public ProducerFactory<String, OrderCreatedDto> orderCreatedProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, OrderCreatedDto> orderCreatedKafkaTemplate() {
        return new KafkaTemplate<>(orderCreatedProducerFactory());
    }

    @Bean
    public ProducerFactory<String, PaymentStatusDto> orderStatusProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, PaymentStatusDto> orderStatusKafkaTemplate() {
        return new KafkaTemplate<>(orderStatusProducerFactory());
    }
}
