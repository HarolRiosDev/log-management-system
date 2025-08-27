package com.hr.eventingestor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogEvent {

    private String serviceName;

    private String id;

    private OffsetDateTime timestamp;

    private String level;

    private String message;

    private String sourceIp;

    private String encryptedMessage;

    private String hashedSourceIp;
}
