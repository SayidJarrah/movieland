package com.dkorniichuk.movieland.service.impl;

import com.dkorniichuk.movieland.service.ReportService;
import com.dkorniichuk.movieland.service.report.Report;
import com.dkorniichuk.movieland.service.report.ReportFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public void addRequest(String reportRequest) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(reportRequest);
        String reportType = node.get("reportType").asText();
        Report report = ReportFactory.getReport(reportType);
        report.create();

    }
}
