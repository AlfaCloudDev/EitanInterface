package com.eitanmedical.app.bydimporter.inbounddelivery.services;

import com.eitanmedical.app.bydimporter.inbounddelivery.boundries.InboundFTPFileDto;
import com.eitanmedical.app.bydimporter.inbounddelivery.boundries.InboundDeliveryCreationDto;
import com.eitanmedical.app.bydimporter.common.services.BYDODataService;
import com.eitanmedical.app.bydimporter.common.services.FtpFileMover;
import com.eitanmedical.app.bydimporter.common.services.FtpFileReader;
import com.eitanmedical.app.bydimporter.common.services.FtpFileUploader;
import com.eitanmedical.app.bydimporter.inbounddelivery.boundries.InBoundDeliveryBTPLogFileDto;
import com.eitanmedical.app.bydimporter.inbounddelivery.boundries.InBoundFileNamePostBYDDto;
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
public class FileInboundProcessingService implements FileInboundProcessingInterface {
    @Autowired
    private FtpFileReader ftpFileReader;

    @Autowired
    private FtpFileMover ftpFileMover;

    @Autowired
    private FtpFileUploader ftpFileUploader;

    @Autowired
    private BYDODataService byDODataService;

    private final ObjectMapper objectMapper;
    private final String postDeliveryCreationURL = "https://my353793.sapbydesign.com/sap/byd/odata/cust/v1/interface_outbounddelivery/InboundDeliveryCreationRootCollection";
    private static final String INPUT_DIRECTORY_PATH = "/drivehqshare/rgwoodfield/Test/OUT/GoodsReceipt/Input";
    private static final String ERROR_DIRECTORY_PATH = "/drivehqshare/rgwoodfield/Test/OUT/GoodsReceipt/Error";
    private static final String SUCCESS_DIRECTORY_PATH = "/drivehqshare/rgwoodfield/Test/OUT/GoodsReceipt/Success";

    public FileInboundProcessingService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String processAllFilesAndSendToByD() throws IOException {
        List<String> byDResponses = new ArrayList<>();
        List<FtpFileReader.FileContentAndPath> fileContentsAndPaths = ftpFileReader.readFiles(INPUT_DIRECTORY_PATH);

        InBoundDeliveryBTPLogFileDto logFile = new InBoundDeliveryBTPLogFileDto();
        logFile.setHeader(new InBoundDeliveryBTPLogFileDto.LogHeaderDto(new Date()));
        List<InBoundDeliveryBTPLogFileDto.FileLogEntryDto> fileLogEntries = new ArrayList<>();

        for (FtpFileReader.FileContentAndPath fileContentAndPath : fileContentsAndPaths) {
            String content = fileContentAndPath.getContent();
            String fileName = fileContentAndPath.getFileName();
            String originalFilePath = INPUT_DIRECTORY_PATH + "/" + fileName;
            String errorFilePath = ERROR_DIRECTORY_PATH + "/" + fileName;
        
            boolean isMoved = ftpFileMover.moveFile(originalFilePath, errorFilePath);
            if (!isMoved) {
                System.out.println("Failed to move file: " + fileName);
                continue;
            }
        
            // Process the file content after moving it to the error directory
            InboundFTPFileDto ftpFileDto = objectMapper.readValue(content, InboundFTPFileDto.class);
            List<String> validationErrors = FileInboundValidationService.getValidationErrors(ftpFileDto);
        
            if (!validationErrors.isEmpty()) {
                InBoundDeliveryBTPLogFileDto.FileLogEntryDto fileLogEntry = new InBoundDeliveryBTPLogFileDto.FileLogEntryDto();
                fileLogEntry.setFileName(fileName);
                fileLogEntry.setErrorMessages(validationErrors);
                fileLogEntries.add(fileLogEntry);
                continue;
            }
        
            InboundDeliveryCreationDto inboundDelivery = new InboundDeliveryCreationDto();
            inboundDelivery.setUniquRequestID(ftpFileDto.getID());
            inboundDelivery.setFileName(fileName);
        
            // Process items and serial numbers
            List<InboundDeliveryCreationDto.InboundDeliveryCreationItem> creationItems = 
                ftpFileDto.getItems().stream().map(item -> {
                    InboundDeliveryCreationDto.InboundDeliveryCreationItem creationItem = 
                        new InboundDeliveryCreationDto.InboundDeliveryCreationItem();
                    creationItem.setLineItem(item.getLineItem());
                    creationItem.setProductID(item.getProductID());
                    creationItem.setActualQuantity(item.getQuantity().toString());
                    creationItem.setIdentifiedStock(item.getLotCode());
        
                    List<InboundDeliveryCreationDto.InboundDeliveryCreationSerial> serials = 
                        Optional.ofNullable(item.getSerialNumbers())
                            .orElseGet(Collections::emptyList)
                            .stream()
                            .map(serialNumberDto -> 
                                new InboundDeliveryCreationDto.InboundDeliveryCreationSerial(serialNumberDto.getSerialNumber()))
                            .collect(Collectors.toList());
        
                    creationItem.setInboundDeliveryCreationSerials(serials);
                    return creationItem;
                }).collect(Collectors.toList());
        
            inboundDelivery.setInboundDeliveryCreationItems(creationItems);
        
            String postBody = objectMapper.writeValueAsString(inboundDelivery);
            String byDResponse = byDODataService.sendPostRequestToByD(postDeliveryCreationURL, postBody, fileName);
            byDResponses.add(byDResponse);
        }
        
        if (!fileLogEntries.isEmpty()) {
            logFile.setFileLogEntries(fileLogEntries);
            String logFileName = createLogFileName();
            String logContent = convertLogToFileToJson(logFile);
            ftpFileUploader.uploadFile(ERROR_DIRECTORY_PATH, logFileName, logContent);
        }
        String responseString = "";
        if (!byDResponses.isEmpty()) {
            responseString = String.join("\n##\n", byDResponses) + "\n##\n";
        }
        return responseString;
    }

    private String convertLogToFileToJson(InBoundDeliveryBTPLogFileDto logFile) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(logFile);
    }

    private String createLogFileName() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HHmmss");
        String timestamp = LocalDateTime.now(ZoneId.of("UTC")).format(dtf);
        return "LogFile_" + timestamp + ".json";
    }

    
    public void finalizeFileProcessing(String fileName, InBoundFileNamePostBYDDto.FileDestination destination) throws IOException {
    String sourceFilePath = ERROR_DIRECTORY_PATH + "/" + fileName;
    String destinationFilePath;

    //if (destination == OutBoundFileNamePostBYDDto.FileDestination.SUCCESS) {
        destinationFilePath = SUCCESS_DIRECTORY_PATH + "/" + fileName;
    // } else {
    //     destinationFilePath = ERROR_DIRECTORY_PATH + "/" + fileName;
    // }

    ftpFileMover.moveFile(sourceFilePath, destinationFilePath);
}

    @Override
    public void createLog(String logContents) throws IOException {
        String logFileName = createLogFileName();
        ftpFileUploader.uploadFile(ERROR_DIRECTORY_PATH, logFileName, logContents);
    }
    
}
