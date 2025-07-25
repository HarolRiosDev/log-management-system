package com.hr.eventingestor.service;

import com.hr.eventingestor.web.dto.IngestLogEventsBatch200ResponseDTO;
import com.hr.eventingestor.web.dto.IngestResponseDTO;
import com.hr.eventingestor.web.dto.LogEventRequestDTO;

import java.util.List;

/**
 * IngestorService is responsible for processing log events and publishing them to Kafka.
 * It handles the encryption and hashing of sensitive data before sending it to the Kafka topic.
 */
public interface IngestorService {
    /**
     * Processes a single log event request, encrypts sensitive data, and publishes it to Kafka.
     *
     * @param request The log event request containing the details of the log event.
     * @return An IngestResponseDTO containing the ID and timestamp of the processed log event.
     */
    IngestResponseDTO processAndPublishLogEvent(LogEventRequestDTO request);
    /**
     * Processes a batch of log event requests, encrypts sensitive data, and publishes them to Kafka.
     *
     * @param requestList A list of log event requests to be processed.
     * @return An IngestLogEventsBatch200ResponseDTO containing the message and count of processed events.
     */
    IngestLogEventsBatch200ResponseDTO processAndPublishLogEventsBatch(List<LogEventRequestDTO> requestList);
}
