package com.hr.eventingestor.web.config.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr.eventingestor.model.LogEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class KafkaLogEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String kafkaTopic;
    private final ObjectMapper objectMapper;

    public KafkaLogEventProducer(KafkaTemplate<String, String> kafkaTemplate,
                                 @Value("${kafka.topics.ingestor-output}") String kafkaTopic,
                                 ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopic = kafkaTopic;
        this.objectMapper = objectMapper;
        log.info("KafkaLogEventProducer initialized with topic: {}", kafkaTopic);
    }

    public CompletableFuture<Void> publishLogEvent(LogEvent logEvent) {
        try {
            String logEventJson = objectMapper.writeValueAsString(logEvent);
            log.info("Publishing log event to Kafka topic '{}': {}", kafkaTopic, logEvent.getId());

            return kafkaTemplate.send(kafkaTopic, logEvent.getId(), logEventJson) // Usa el ID del log como clave
                    .thenAccept(result -> log.info("Event ID {} successfully published at offset {}", logEvent.getId(), result.getRecordMetadata().offset()))
                    .exceptionally(ex -> {
                        log.error("Error publishing event ID {} to Kafka: {}", logEvent.getId(), ex.getMessage());
                        return null; // Or rethrow, depending on your error handling strategy
                    });
        } catch (JsonProcessingException e) {
            log.error("Error serializing LogEvent to JSON for Kafka: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        } catch (Exception e) {
            log.error("Unexpected error while attempting to publish to Kafka: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }
}