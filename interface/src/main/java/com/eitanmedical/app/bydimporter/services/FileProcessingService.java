package com.eitanmedical.app.bydimporter.services;

import com.eitanmedical.app.bydimporter.boundries.OutboundFTPFileDto;
import com.eitanmedical.app.bydimporter.boundries.OutboundDeliveryCreationDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileProcessingService implements FileProcessingInterface {
    @Autowired
    private FTPReadWriteService ftpReadService;

    @Autowired
    private ByDODataService byDODataService;

    private final ObjectMapper objectMapper;

    @Value("${EITAN_INTERFACE_FTP_PASSWORD:rhcl1234}")
    private String ftpPassword;
    @Value("${EITAN_INTERFACE_FTP_USERNAME:rhcleitan}")
    private String ftpUser;
    @Value("${EITAN_INTERFACE_FTP_SERVER:ftp.drivehq.com}")
    private String ftpServer;
    @Value("${EITAN_INTERFACE_FTP_PORT:21}")
    private int ftpPort;

    private final String ftpDirectoryPath = "/drivehqshare/rgwoodfield/Eitan_SAP/Test/OUT/DeliveryNote/Input";
    private final String errorDirectoryPath = "/drivehqshare/rgwoodfield/Eitan_SAP/Test/OUT/DeliveryNote/Error";


    public FileProcessingService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

@Override
public String processAllFilesAndSendToByD() throws IOException {
    List<String> byDResponses = new ArrayList<>();
    List<FTPReadWriteService.FileContent> fileContents = ftpReadService.readAllFiles(ftpServer, ftpUser, ftpPassword, ftpPort, ftpDirectoryPath, errorDirectoryPath);

    for (FTPReadWriteService.FileContent fileContent : fileContents) {
        OutboundFTPFileDto ftpFileDto = objectMapper.readValue(fileContent.getContent(), OutboundFTPFileDto.class);

        if (!FileValidationService.isValidFile(ftpFileDto)) {
            // Handle invalid file
            continue;
        }

        OutboundDeliveryCreationDto outboundDelivery = new OutboundDeliveryCreationDto();
        outboundDelivery.setSalesOrderID(ftpFileDto.getReference());
        outboundDelivery.setShipFromSite(ftpFileDto.getShipFromSite());
        outboundDelivery.setFileName(extractFileName(fileContent.getFilePath())); // Set the filename

            List<OutboundDeliveryCreationDto.OutboundDeliveryCreationItem> creationItems = ftpFileDto.getItems().stream().map(item -> {
                OutboundDeliveryCreationDto.OutboundDeliveryCreationItem creationItem = new OutboundDeliveryCreationDto.OutboundDeliveryCreationItem();
                creationItem.setLineItem(item.getLineItem());
                creationItem.setProductID(item.getProductID());
                creationItem.setActualQuantity(item.getQuantity().toString());
                creationItem.setLogisticsAreaID(item.getLotCode());

                List<OutboundDeliveryCreationDto.OutboundDeliveryCreationSerial> serials = 
                    Optional.ofNullable(item.getSerialNumbers())
                        .orElseGet(Collections::emptyList)
                        .stream()
                        .map(serialNumberDto -> new OutboundDeliveryCreationDto.OutboundDeliveryCreationSerial(serialNumberDto.getSerialNumber()))
                        .collect(Collectors.toList());

                creationItem.setOutboundDeliveryCreationSerials(serials);
                return creationItem;
            }).collect(Collectors.toList());

            outboundDelivery.setOutboundDeliveryCreationItems(creationItems);
            String postBody = objectMapper.writeValueAsString(outboundDelivery);
            String byDResponse = byDODataService.sendPostRequestToByD("https://my353793.sapbydesign.com/sap/byd/odata/cust/v1/outboundtest/OutboundDeliveryCreationRootCollection", postBody);

            byDResponses.add(byDResponse);
        }

        return String.join("\n", byDResponses);
    }

    @Override
    public void finalizeFileProcessing(String fileName) throws IOException {
        String filePath = errorDirectoryPath + "/" + fileName;
        ftpReadService.deleteFile(ftpServer, ftpUser, ftpPassword, ftpPort, filePath);
    }

    // Helper method to extract the filename from the file path
    private String extractFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf('/') + 1);
    }
}