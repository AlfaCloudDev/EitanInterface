package com.eitanmedical.app.bydimporter.outbounddelivery.services;

import com.eitanmedical.app.bydimporter.outbounddelivery.boundries.OutboundFTPFileDto;
import com.eitanmedical.app.bydimporter.outbounddelivery.boundries.OutboundDeliveryCreationDto;
import com.eitanmedical.app.bydimporter.outbounddelivery.boundries.OutBoundDeliveryBTPLogFileDto;
import com.eitanmedical.app.bydimporter.outbounddelivery.boundries.OutBoundFileNamePostBYDDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

@Service
public class FileProcessingService implements FileProcessingInterface {
    @Autowired
    private FtpFileReader ftpFileReader;

    @Autowired
    private FtpFileMover ftpFileMover;

    @Autowired
    private FtpFileUploader ftpFileUploader;

    @Autowired
    private BYDODataService byDODataService;

    private final ObjectMapper objectMapper;
    private final String postDeliveryCreationURL = "https://my353793.sapbydesign.com/sap/byd/odata/cust/v1/interface_outbounddelivery/OutboundDeliveryCreationRootCollection";
    private static final String INPUT_DIRECTORY_PATH = "/drivehqshare/rgwoodfield/Test/OUT/DeliveryNote/Input";
    private static final String ERROR_DIRECTORY_PATH = "/drivehqshare/rgwoodfield/Test/OUT/DeliveryNote/Error";
    private static final String SUCCESS_DIRECTORY_PATH = "/drivehqshare/rgwoodfield/Test/OUT/DeliveryNote/Success";

    public FileProcessingService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String processAllFilesAndSendToByD() throws IOException {
        List<String> byDResponses = new ArrayList<>();
        List<FtpFileReader.FileContentAndPath> fileContentsAndPaths = ftpFileReader.readFiles(INPUT_DIRECTORY_PATH);

        OutBoundDeliveryBTPLogFileDto logFile = new OutBoundDeliveryBTPLogFileDto();
        logFile.setHeader(new OutBoundDeliveryBTPLogFileDto.LogHeaderDto(new Date()));
        List<OutBoundDeliveryBTPLogFileDto.FileLogEntryDto> fileLogEntries = new ArrayList<>();

        for (FtpFileReader.FileContentAndPath fileContentAndPath : fileContentsAndPaths) {
            String content = fileContentAndPath.getContent();
            String fileName = fileContentAndPath.getFileName();
            // String originalFilePath = INPUT_DIRECTORY_PATH + "/" + fileName;
            // String errorFilePath = ERROR_DIRECTORY_PATH + "/" + fileName;
        
            // boolean isMoved = ftpFileMover.moveFile(originalFilePath, errorFilePath);
            // if (!isMoved) {
            //     System.out.println("Failed to move file: " + fileName);
            //     continue;
            // }
        
            // Process the file content after moving it to the error directory
            OutboundFTPFileDto ftpFileDto = objectMapper.readValue(content, OutboundFTPFileDto.class);
            List<String> validationErrors = FileValidationService.getValidationErrors(ftpFileDto);
        
            if (!validationErrors.isEmpty()) {
                OutBoundDeliveryBTPLogFileDto.FileLogEntryDto fileLogEntry = new OutBoundDeliveryBTPLogFileDto.FileLogEntryDto();
                fileLogEntry.setFileName(fileName);
                fileLogEntry.setErrorMessages(validationErrors);
                fileLogEntries.add(fileLogEntry);
                continue;
            }
        
            OutboundDeliveryCreationDto outboundDelivery = new OutboundDeliveryCreationDto();
            // Populate the outboundDelivery object with data from ftpFileDto
            outboundDelivery.setSalesOrderID(ftpFileDto.getReference());
            outboundDelivery.setShipFromSite(ftpFileDto.getShipFromSite());
            outboundDelivery.setUniquRequestID(ftpFileDto.getUniqueRequestID());
            outboundDelivery.setTrackingNumber(ftpFileDto.getTrackingNumbers());
            outboundDelivery.setInternalComment(ftpFileDto.getInternalComment());
            outboundDelivery.setFileName(fileName);
        
            // Process items and serial numbers
            List<OutboundDeliveryCreationDto.OutboundDeliveryCreationItem> creationItems = 
                ftpFileDto.getItems().stream().map(item -> {
                    OutboundDeliveryCreationDto.OutboundDeliveryCreationItem creationItem = 
                        new OutboundDeliveryCreationDto.OutboundDeliveryCreationItem();
                    creationItem.setLineItem(item.getLineItem());
                    creationItem.setProductID(item.getProductID());
                    creationItem.setActualQuantity(item.getQuantity().toString());
                    creationItem.setIdentifiedStock(item.getLotCode());
        
                    List<OutboundDeliveryCreationDto.OutboundDeliveryCreationSerial> serials = 
                        Optional.ofNullable(item.getSerialNumbers())
                            .orElseGet(Collections::emptyList)
                            .stream()
                            .map(serialNumberDto -> 
                                new OutboundDeliveryCreationDto.OutboundDeliveryCreationSerial(serialNumberDto.getSerialNumber()))
                            .collect(Collectors.toList());
        
                    creationItem.setOutboundDeliveryCreationSerials(serials);
                    return creationItem;
                }).collect(Collectors.toList());
        
            outboundDelivery.setOutboundDeliveryCreationItems(creationItems);
        
            String postBody = objectMapper.writeValueAsString(outboundDelivery);
            String byDResponse = byDODataService.sendPostRequestToByD(postDeliveryCreationURL, postBody);
            byDResponses.add(byDResponse);
        }
        
        if (!fileLogEntries.isEmpty()) {
            logFile.setFileLogEntries(fileLogEntries);
            String logFileName = createLogFileName();
            String logContent = convertLogToFileToJson(logFile);
            ftpFileUploader.uploadFile(ERROR_DIRECTORY_PATH, logFileName, logContent);
        }
        
        return String.join("\n", byDResponses);
    }

    private String convertLogToFileToJson(OutBoundDeliveryBTPLogFileDto logFile) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(logFile);
    }

    private String createLogFileName() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HHmmss");
        String timestamp = LocalDateTime.now(ZoneId.of("UTC")).format(dtf);
        return "LogFile_" + timestamp + ".json";
    }

    
    public void finalizeFileProcessing(String fileName, OutBoundFileNamePostBYDDto.FileDestination destination) throws IOException {
    String sourceFilePath = INPUT_DIRECTORY_PATH + "/" + fileName;
    String destinationFilePath;

    if (destination == OutBoundFileNamePostBYDDto.FileDestination.SUCCESS) {
        destinationFilePath = SUCCESS_DIRECTORY_PATH + "/" + fileName;
    } else {
        destinationFilePath = ERROR_DIRECTORY_PATH + "/" + fileName;
    }

    ftpFileMover.moveFile(sourceFilePath, destinationFilePath);
}

    @Override
    public void createLog(String logContents) throws IOException {
        String logFileName = createLogFileName();
        ftpFileUploader.uploadFile(ERROR_DIRECTORY_PATH, logFileName, logContents);
    }
    
}