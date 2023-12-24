package com.eitanmedical.app.bydimporter.services;

import com.eitanmedical.app.bydimporter.boundries.FTPFileDto;
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
    private FTPReadService ftpReadService;

    @Autowired
    private ByDODataService byDODataService;

    private final ObjectMapper objectMapper;

    private final String ftpServer = "ftp.drivehq.com";
    private final String ftpUser = "rhcleitan";
    //private final String ftpPassword = "rhcl1234";
    private final int ftpPort = 21;
    private final String ftpDirectoryPath = "/drivehqshare/rgwoodfield/Eitan_SAP/Test/OUT/DeliveryNote/Input";
    private final String errorDirectoryPath = "/drivehqshare/rgwoodfield/Eitan_SAP/Test/OUT/DeliveryNote/Error";

    @Value("${EITAN_INTERFACE_FTP_PASSWORD:default_value}")
    private String ftpPassword;

    public FileProcessingService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String processAllFilesAndSendToByD() throws IOException {
        List<String> byDResponses = new ArrayList<>();
        List<FTPReadService.FileContent> fileContents = ftpReadService.readAllFiles(ftpServer, ftpUser, ftpPassword, ftpPort, ftpDirectoryPath);

        for (FTPReadService.FileContent fileContent : fileContents) {
            FTPFileDto ftpFileDto = objectMapper.readValue(fileContent.getContent(), FTPFileDto.class);

            if (!FileValidationService.isValidFile(ftpFileDto)) {
                // Move file to error directory if validation fails
                ftpReadService.moveToErrorDirectory(ftpServer, ftpUser, ftpPassword, ftpPort, fileContent.getFilePath(), errorDirectoryPath);
                continue;
            }

            OutboundDeliveryCreationDto outboundDelivery = new OutboundDeliveryCreationDto();
            outboundDelivery.setSalesOrderID(ftpFileDto.getReference());
            outboundDelivery.setShipFromSite(ftpFileDto.getShipFromSite());

            List<OutboundDeliveryCreationDto.OutboundDeliveryCreationItem> creationItems = ftpFileDto.getItems().stream().map(item -> {
                OutboundDeliveryCreationDto.OutboundDeliveryCreationItem creationItem = new OutboundDeliveryCreationDto.OutboundDeliveryCreationItem();
                creationItem.setLineItem(item.getLineItem());
                creationItem.setProductID(item.getProductID());
                creationItem.setActualQuantity(item.getQuantity().toString());
                creationItem.setLogisticsAreaID("TEST");

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
}