package com.dkorniichuk.movieland.controller;

import com.dkorniichuk.movieland.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/v1/report")
public class ReportController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ReportService reportService;


    @RequestMapping(value = "/addRequest", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addReportRequest(@RequestBody String reportRequest) throws IOException {
        logger.info("Sending request to create report");
         reportService.addRequest(reportRequest);
    }


}
