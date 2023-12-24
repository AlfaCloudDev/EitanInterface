package com.example.demo.bydimporter.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bydimporter.services.FileProcessingInterface;

import java.io.IOException;


@RestController
public class CreateOutboundDeliveryController {
    private final FileProcessingInterface itemProcessingService;

    public CreateOutboundDeliveryController(FileProcessingInterface itemProcessingService) {
        this.itemProcessingService = itemProcessingService;
    }
    
    @GetMapping(path = "/CreateOutboundDeliveryNew") //CreateOutboundDelivery
    public String createOutboundDelivery() throws IOException {
        return itemProcessingService.processAllFilesAndSendToByD();
    }
}