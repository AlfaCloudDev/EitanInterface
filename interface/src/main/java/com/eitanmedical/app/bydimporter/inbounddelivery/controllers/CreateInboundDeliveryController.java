package com.eitanmedical.app.bydimporter.inbounddelivery.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import com.eitanmedical.app.bydimporter.inbounddelivery.boundries.InBoundFileNamePostBYDDto;
import com.eitanmedical.app.bydimporter.inbounddelivery.services.FileInboundProcessingInterface;

import java.io.IOException;

@RestController
public class CreateInboundDeliveryController {
    private final FileInboundProcessingInterface itemProcessingService;

    public CreateInboundDeliveryController(FileInboundProcessingInterface itemProcessingService) {
        this.itemProcessingService = itemProcessingService;
    }
    
    @GetMapping(path = "/CreateInboundDeliveryNew") 
    public String createInboundDelivery(Authentication authentication) throws IOException {
        return itemProcessingService.processAllFilesAndSendToByD();
    }

    @PostMapping(path = "/InboundPostProcessFiles")
    public String postProcessFiles(@RequestBody InBoundFileNamePostBYDDto fileNameDto) throws IOException {
        itemProcessingService.finalizeFileProcessing(fileNameDto.getFileName(), fileNameDto.getDestination());
        return "File Processed";
    }

    @PostMapping(path = "/InboundCreateLog", consumes = "text/plain")
    public String createLog(@RequestBody String logContents) throws IOException {
        itemProcessingService.createLog(logContents);
        return "Log Created";
    }
}