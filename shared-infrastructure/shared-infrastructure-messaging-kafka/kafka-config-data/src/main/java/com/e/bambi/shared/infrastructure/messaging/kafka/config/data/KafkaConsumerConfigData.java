package com.e.bambi.shared.infrastructure.messaging.kafka.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-consumer-config")
public class KafkaConsumerConfigData {
    private String specificAvroReader;
    private String keyDeserializer;
    private String valueDeserializer;
    private Boolean enableAutoCommit;
    private String autoOffsetReset;
}
