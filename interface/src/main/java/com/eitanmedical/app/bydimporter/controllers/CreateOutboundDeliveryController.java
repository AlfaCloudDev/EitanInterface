package com.eitanmedical.app.bydimporter.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.eitanmedical.app.bydimporter.boundries.FileNamePostBYDDto;
import com.eitanmedical.app.bydimporter.services.FileProcessingInterface;

import java.io.IOException;


@RestController
public class CreateOutboundDeliveryController {
    private final FileProcessingInterface itemProcessingService;

    public CreateOutboundDeliveryController(FileProcessingInterface itemProcessingService) {
        this.itemProcessingService = itemProcessingService;
    }
    
    @GetMapping(path = "/CreateOutboundDeliveryNew") //CreateOutboundDelivery
    public String createOutboundDelivery(Authentication authentication) throws IOException {
        return itemProcessingService.processAllFilesAndSendToByD();
    }

    @PostMapping(path = "/MoveFileToSuccess")
    public String moveFileToSuccess(@RequestBody FileNamePostBYDDto fileNameDto) throws IOException {
        itemProcessingService.moveFileToSuccessFolder(fileNameDto.getFileName());
        return "File moved to Success folder";
    }
}