package com.eitanmedical.app.bydimporter.services;

import com.eitanmedical.app.bydimporter.boundries.OutboundFTPFileDto;
import com.eitanmedical.app.bydimporter.boundries.OutboundDeliveryCreationDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.eitanmedical.app.bydimporter.boundries.OutBoundDeliveryBTPLogFileDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

@Service
public class FileProcessingService implements FileProcessingInterface {
    @Autowired
    private FTPReadWriteService ftpReadService;

    @Autowired
    private BYDODataService byDODataService;

    private final ObjectMapper objectMapper;
    
    private final String postDeliveryCreationURL = "https://my353793.sapbydesign.com/sap/byd/odata/cust/v1/outboundtest/OutboundDeliveryCreationRootCollection";

    public FileProcessingService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String processAllFilesAndSendToByD() throws IOException {
        List<String> byDResponses = new ArrayList<>();
        List<FTPReadWriteService.FileContent> fileContents = ftpReadService.readAllFiles();

        OutBoundDeliveryBTPLogFileDto logFile = new OutBoundDeliveryBTPLogFileDto();
        logFile.setHeader(new OutBoundDeliveryBTPLogFileDto.LogHeaderDto(new Date()));
        List<OutBoundDeliveryBTPLogFileDto.FileLogEntryDto> fileLogEntries = new ArrayList<>();

        for (FTPReadWriteService.FileContent fileContent : fileContents) {
            OutboundFTPFileDto ftpFileDto = objectMapper.readValue(fileContent.getContent(), OutboundFTPFileDto.class);
            List<String> validationErrors = FileValidationService.getValidationErrors(ftpFileDto);
        
            if (!validationErrors.isEmpty()) {
                OutBoundDeliveryBTPLogFileDto.FileLogEntryDto fileLogEntry = new OutBoundDeliveryBTPLogFileDto.FileLogEntryDto();
                String fileNameOnly = extractFileName(fileContent.getFilePath());
                fileLogEntry.setFileName(fileNameOnly);
                fileLogEntry.setErrorMessages(validationErrors);
                fileLogEntries.add(fileLogEntry);
                continue;
            }

            OutboundDeliveryCreationDto outboundDelivery = new OutboundDeliveryCreationDto();
            outboundDelivery.setSalesOrderID(ftpFileDto.getReference());
            outboundDelivery.setShipFromSite(ftpFileDto.getShipFromSite());
            outboundDelivery.setUniquRequestID(ftpFileDto.getUniqueRequestID());
            outboundDelivery.setTrackingNumber(ftpFileDto.getTrackingNumbers());
            outboundDelivery.setInternalComment(ftpFileDto.getInternalComment());
            outboundDelivery.setFileName(extractFileName(fileContent.getFilePath())); 

            List<OutboundDeliveryCreationDto.OutboundDeliveryCreationItem> creationItems = ftpFileDto.getItems()
                .stream().map(item -> {
                    OutboundDeliveryCreationDto.OutboundDeliveryCreationItem creationItem = new OutboundDeliveryCreationDto.OutboundDeliveryCreationItem();
                    creationItem.setLineItem(item.getLineItem());
                    creationItem.setProductID(item.getProductID());
                    creationItem.setActualQuantity(item.getQuantity().toString());
                    creationItem.setIdentifiedStock(item.getLotCode());

                    List<OutboundDeliveryCreationDto.OutboundDeliveryCreationSerial> serials = Optional
                            .ofNullable(item.getSerialNumbers())
                            .orElseGet(Collections::emptyList)
                            .stream()
                            .map(serialNumberDto -> new OutboundDeliveryCreationDto.OutboundDeliveryCreationSerial(
                                    serialNumberDto.getSerialNumber()))
                            .collect(Collectors.toList());

                    creationItem.setOutboundDeliveryCreationSerials(serials);
                    return creationItem;
                }).collect(Collectors.toList());

            outboundDelivery.setOutboundDeliveryCreationItems(creationItems);
            String postBody = objectMapper.writeValueAsString(outboundDelivery);
            System.out.println("postBody:" + postBody);
            String byDResponse = byDODataService.sendPostRequestToByD(postDeliveryCreationURL, postBody);

            byDResponses.add(byDResponse);
        }

        logFile.setFileLogEntries(fileLogEntries);

        if (!fileLogEntries.isEmpty()) {
            String logFileName = createLogFileName();
            String logContent = convertLogToFileToJson(logFile);
            ftpReadService.uploadLogFile(logFileName, logContent); // Call on the instance
        }

        return String.join("\n", byDResponses);
    }

    private String convertLogToFileToJson(OutBoundDeliveryBTPLogFileDto logFile) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(logFile);
    }


    private String extractFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf('/') + 1);
    }

    public void finalizeFileProcessing(String fileName) throws IOException {
        // Assuming errorDirectoryPath and successDirectoryPath are defined in FTPReadWriteService
        String sourceFilePath = FTPReadWriteService.ERROR_DIRECTORY_PATH + "/" + fileName;
        String destinationFilePath = FTPReadWriteService.SUCCESS_DIRECTORY_PATH + "/" + fileName;
    
        ftpReadService.moveFile(sourceFilePath, destinationFilePath);
    }

    @Override
    public void createLog(String logContents) throws IOException {
        String logFileName = createLogFileName();
        ftpReadService.uploadLogFile(logFileName, logContents);
    }

    private String createLogFileName() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HHmmss");
        String timestamp = LocalDateTime.now(ZoneId.of("UTC")).format(dtf);
        System.out.println("timestamp: " + timestamp);
        return "LogFile_" + timestamp + ".json";
    }

}