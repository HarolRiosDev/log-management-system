package com.hr.eventingestor.web.controller;

import com.hr.eventingestor.service.IngestorService;
import com.hr.eventingestor.web.api.IngestaDeLogsAPI;
import com.hr.eventingestor.web.dto.IngestLogEventsBatch200ResponseDTO;
import com.hr.eventingestor.web.dto.IngestResponseDTO;
import com.hr.eventingestor.web.dto.LogEventRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IngestController implements IngestaDeLogsAPI {

    private final IngestorService ingestorService;
    @Override
    public ResponseEntity<IngestResponseDTO> ingestLogEvent(LogEventRequestDTO logEventRequestDTO) {
       var response =  ingestorService.processAndPublishLogEvent(logEventRequestDTO);
       return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<IngestLogEventsBatch200ResponseDTO> ingestLogEventsBatch(List<LogEventRequestDTO> logEventRequestDTO) {
        var response =  ingestorService.processAndPublishLogEventsBatch(logEventRequestDTO);
        return ResponseEntity.ok(response);
    }
}
