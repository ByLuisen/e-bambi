package com.e.bambi.shared.infrastructure.messaging.kafka.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-producer-config")
public class KafkaProducerConfigData {
    private String keySerializerClass;
    private String valueSerializerClass;
    private String enableIdempotence;
    private String acks;
    private Integer retryCount;
    private Integer lingerMs;
    private Integer batchSize;
    private String compressionType;
}
