package com.eitanmedical.app.bydimporter.outbounddelivery.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.eitanmedical.app.bydimporter.outbounddelivery.boundries.OutBoundFileNamePostBYDDto;
import com.eitanmedical.app.bydimporter.outbounddelivery.services.FileProcessingInterface;

import java.io.IOException;


@RestController
public class CreateOutboundDeliveryController {
    private final FileProcessingInterface itemProcessingService;

    public CreateOutboundDeliveryController(FileProcessingInterface itemProcessingService) {
        this.itemProcessingService = itemProcessingService;
    }
    
    @GetMapping(path = "/CreateOutboundDeliveryNew") //CreateOutboundDelivery
    public String createOutboundDelivery(Authentication authentication) throws IOException {
    //public String createOutboundDelivery() throws IOException {
        return itemProcessingService.processAllFilesAndSendToByD();
    }

    @PostMapping(path = "/PostProcessFiles")
    public String PostProcessFiles(@RequestBody OutBoundFileNamePostBYDDto fileNameDto) throws IOException {
        itemProcessingService.finalizeFileProcessing(fileNameDto.getFileName());
        return "File Deleted";
    }

    @PostMapping(path = "/createLog", consumes = "text/plain")
    public String CreateLog(@RequestBody String logContents) throws IOException {
        itemProcessingService.createLog(logContents);
        return "Log Created";
    }

}