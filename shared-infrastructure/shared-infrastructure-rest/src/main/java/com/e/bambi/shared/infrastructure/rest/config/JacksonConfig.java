package com.e.bambi.shared.infrastructure.rest.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            SimpleModule module = new SimpleModule();

            module.addSerializer(Instant.class, new JsonSerializer<Instant>() {
                @Override
                public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    Instant truncated = value.truncatedTo(ChronoUnit.MILLIS);
                    gen.writeString(truncated.toString());
                }
            });

            module.addSerializer(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>() {
                @Override
                public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    OffsetDateTime truncated = value.truncatedTo(ChronoUnit.MILLIS);
                    gen.writeString(truncated.toString());
                }
            });

            module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
                @Override
                public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    OffsetDateTime odt = OffsetDateTime.parse(p.getValueAsString());
                    return odt.truncatedTo(ChronoUnit.MILLIS);
                }
            });

            module.addSerializer(ZonedDateTime.class, new JsonSerializer<ZonedDateTime>() {
                @Override
                public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeString(value.truncatedTo(ChronoUnit.MILLIS).toString());
                }
            });

            builder.modules(new JavaTimeModule(), module)
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        };
    }
}


