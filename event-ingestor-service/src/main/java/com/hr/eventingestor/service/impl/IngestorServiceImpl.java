package com.hr.eventingestor.service.impl;

import com.hr.eventingestor.model.LogEvent;
import com.hr.eventingestor.service.IngestorService;
import com.hr.eventingestor.web.config.crypto.LogSecurityProcessor;
import com.hr.eventingestor.web.config.kafka.KafkaLogEventProducer;
import com.hr.eventingestor.web.dto.IngestLogEventsBatch200ResponseDTO;
import com.hr.eventingestor.web.dto.IngestResponseDTO;
import com.hr.eventingestor.web.dto.LogEventRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * IngestorServiceImpl is responsible for processing log events and publishing them to Kafka.
 * It handles the encryption and hashing of sensitive data before sending it to the Kafka topic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IngestorServiceImpl implements IngestorService {
    /**
     * The LogSecurityProcessor is used to handle security operations such as hashing and encryption.
     */
    private final LogSecurityProcessor securityProcessor;
    /**
     * The KafkaLogEventProducer is responsible for publishing log events to a Kafka topic.
     */
    private final KafkaLogEventProducer kafkaLogEventProducer;

    @Override
    public IngestResponseDTO processAndPublishLogEvent(LogEventRequestDTO request){
        var processedLogEvent = createAndProcessLogEvent(request);
        log.info("Processed log event: ID={}, Level={}, Hashed IP={}",
                processedLogEvent.getId(),
                processedLogEvent.getLevel(),
                processedLogEvent.getHashedSourceIp());

        kafkaLogEventProducer.publishLogEvent(processedLogEvent);
        return new IngestResponseDTO()
                .id(UUID.fromString(processedLogEvent.getId()))
                .timestamp(OffsetDateTime.from(processedLogEvent.getTimestamp()))
                .message("Log event processed and published successfully. "+ processedLogEvent.getId());
    }
    @Override
    public IngestLogEventsBatch200ResponseDTO processAndPublishLogEventsBatch(List<LogEventRequestDTO> requestList) {
        IngestLogEventsBatch200ResponseDTO response = new IngestLogEventsBatch200ResponseDTO();
        if (requestList == null || requestList.isEmpty()) {
            response.setProcessedCount(0);
            return response;
        }
        long processedCount = requestList.parallelStream()
                .map(request -> {
                    log.info("Processing batch event for IP: {}", request.getSourceIp());
                    LogEvent processedLogEvent = createAndProcessLogEvent(request);
                    kafkaLogEventProducer.publishLogEvent(processedLogEvent);
                    return processedLogEvent;
                })
                .toList().size();

        log.info("Batch of {} log events successfully processed and published.", processedCount);
        response.setProcessedCount((int) processedCount);
        response.setMessage("Batch of " + processedCount + " log events received and processed.");
        return response;

    }

    /**
     * Creates and processes a log event by hashing the source IP and encrypting the message.
     *
     * @param request The log event request containing the source IP, level, and message.
     * @return A LogEvent object containing the processed data.
     */
    private LogEvent createAndProcessLogEvent(LogEventRequestDTO request) {
        UUID eventId = UUID.randomUUID();
        Instant timestamp = Instant.now();

        String hashedSourceIp = securityProcessor.hashSha256(request.getSourceIp());
        String encryptedMessage = securityProcessor.encryptAes(request.getMessage());
        return LogEvent.builder()
                .id(eventId.toString())
                .timestamp(timestamp)
                .sourceIp(request.getSourceIp())
                .level(request.getLevel().toString())
                .message(request.getMessage())
                .hashedSourceIp(hashedSourceIp)
                .encryptedMessage(encryptedMessage)
                .build();
    }
}
