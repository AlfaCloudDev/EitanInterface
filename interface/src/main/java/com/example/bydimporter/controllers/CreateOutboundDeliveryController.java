package com.example.bydimporter.controllers;

import com.example.bydimporter.services.FileProcessingInterface;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;


@RestController
public class CreateOutboundDeliveryController {
    private final FileProcessingInterface itemProcessingService;

    public CreateOutboundDeliveryController(FileProcessingInterface itemProcessingService) {
        this.itemProcessingService = itemProcessingService;
    }
    
    @GetMapping(path = "/CreateOutboundDelivery") 
    public String createOutboundDelivery() throws IOException {
        return itemProcessingService.processAllFilesAndSendToByD();
    }
}