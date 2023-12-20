package com.example.demo.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.io.IOUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@RestController
public class CreateOutboundDeliveryController {

    private final String oDataServiceUri = "https://my353793.sapbydesign.com/sap/byd/odata/cust/v1/outboundtest/OutboundDeliveryCreationRootCollection";
    private final String oDataItemUri = "https://my353793.sapbydesign.com/sap/byd/odata/cust/v1/outboundtest/OutboundDeliveryCreationItemCollection";
    private final String oDataSerialUri = "https://my353793.sapbydesign.com/sap/byd/odata/cust/v1/outboundtest/OutboundDeliveryCreationSerialCollection";
    private final Logger logger = LoggerFactory.getLogger(CreateOutboundDeliveryController.class);

    @GetMapping(path = "/CreateOutBoundDelivery", produces = MediaType.APPLICATION_JSON_VALUE)
    public String createOutBoundDelivery() {
        String server = "ftp.drivehq.com";
        String user = "rhcleitan";
        String password = "rhcl1234";
        int port = 21;
        FTPClient ftpClient = new FTPClient();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> responses = new ArrayList<>();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory("/drivehqshare/rgwoodfield/Eitan_SAP/Test/OUT/DeliveryNote/Input");

            FTPFile[] files = ftpClient.listFiles();

            for (FTPFile file : files) {
                if (!file.isFile()) {
                    continue;
                }

                InputStream inputStream = ftpClient.retrieveFileStream(file.getName());
                String jsonContent = IOUtils.toString(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                JsonNode rootNode = objectMapper.readTree(jsonContent);

                String shipFromSite = safeGetAsText(rootNode, "ShipFromSite");
                String reference = safeGetAsText(rootNode, "Reference");
                if(reference.contains("EMU")){
                    reference = reference.substring(3);
                }

                String postBody = String.format("{\"SalesOrderID\":\"%s\", \"ShipFromSite\":\"%s\"}", reference, shipFromSite);
                String response = sendPostRequest(postBody);
                String salesOrderObjectID ="";
                if (response.contains("ObjectID")) {
                    logger.info("salesOrderObjectID exists");
                    salesOrderObjectID = extractObjectID(response);
                    logger.info("salesOrderObjectID: " + salesOrderObjectID);
                }else{
                    logger.info("ObjectID not found");
                }
                responses.add(response);

                //ITEMS

                JsonNode items = rootNode.path("Item");
                for (JsonNode item : items) {
                    
                    String productID = safeGetAsText(item, "ProductID");
                    String losgisticsAreaID = safeGetAsText(item, "lotCode");
                    JsonNode serialNumbers = item.path("SerialNumbers");
                    String actualQuantity = safeGetAsText(item, "Quantity");
                    String lineItem = safeGetAsText(item, "LineItem");
                    
                    // int serialCount = 0;
                    // for (JsonNode serialNode : serialNumbers) {
                    //     if (serialNode.has("SerialNumber") && !serialNode.get("SerialNumber").isNull()) {
                    //         serialCount++;
                    //     }
                    // }

                    String postItemBody = String.format("{\"ParentObjectID\":\"%s\",\"ProductID\":\"%s\", \"LogisticsAreaID\":\"%s\" , \"ActualQuantity\":\"%s\" , \"LineItem\":\"%s\"}",salesOrderObjectID ,productID, losgisticsAreaID, actualQuantity, lineItem);
                    String itemResponse = sendItemPostRequest(postItemBody);
                    responses.add(itemResponse);

                    logger.info("Response for Item POST request: " + itemResponse);
                        
                    if (itemResponse.contains("ObjectID")) {
                        logger.info("Item ObjectId exists");
                        String itemObjectID = extractObjectID(itemResponse);
                        logger.info("Item ObjectId: " + itemObjectID);
                        
                        //SERIALS

                        for (JsonNode serialNode : serialNumbers) {
                            String serialID = safeGetAsText(serialNode, "SerialNumber");

                            if (serialID != null) {
                                String serialPostBody = String.format("{\"ParentObjectID\":\"%s\", \"SerialID\":\"%s\"}", itemObjectID, serialID);
                                String responseSerial = sendSerialPostRequest(serialPostBody);
                                responses.add(responseSerial);
                                logger.info("Sent POST request for serial number: " + serialID);
                            } else {
                                logger.info("Serial number missing or null in the serialNode: " + serialNode);
                            }
                        }
                    } else {
                        logger.error("Failed to extract ObjectID from response");
                    }
                }
                ftpClient.completePendingCommand();
            }
        } catch (IOException e) {
            logger.error("Error occurred: " + e.getMessage(), e);
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.error("Error while disconnecting FTP: " + e.getMessage(), e);
            }
        }
        return responses.toString();
    }

    private String sendPostRequest(String postBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic X29kYXRhOldlbGNvbWUxMjM=");

        HttpEntity<String> request = new HttpEntity<>(postBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(oDataServiceUri, request, String.class);
            return response.getStatusCode() + " - " + response.getBody();
        } catch (Exception e) {
            logger.error("Failed to send POST request: " + e.getMessage(), e);
            return "Failed to send POST request: " + e.getMessage();
        }
    }

    private String sendItemPostRequest(String postBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic X29kYXRhOldlbGNvbWUxMjM=");

        HttpEntity<String> request = new HttpEntity<>(postBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(oDataItemUri, request, String.class);
            return response.getStatusCode() + " - " + response.getBody();
            
        } catch (Exception e) {
            logger.error("Failed to send Item POST request: " + e.getMessage(), e);
            return "Failed to send Item POST request: " + e.getMessage();
        }
    }

    private String sendSerialPostRequest(String postBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic X29kYXRhOldlbGNvbWUxMjM=");

        HttpEntity<String> request = new HttpEntity<>(postBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(oDataSerialUri, request, String.class);
            return response.getStatusCode() + " - " + response.getBody();
            
        } catch (Exception e) {
            logger.error("Failed to send serial POST request: " + e.getMessage(), e);
            return "Failed to send serial POST request: " + e.getMessage();
        }
    }

    private String extractObjectID(String response) {
        try {
            String objectIDKey = "\"ObjectID\":\"";
            int startIndex = response.indexOf(objectIDKey);
            if (startIndex == -1) {
                logger.error("ObjectID key not found in response");
                return null;
            }
   
            startIndex += objectIDKey.length();
            int endIndex = response.indexOf("\"", startIndex);
            if (endIndex == -1) {
                logger.error("End of ObjectID value not found in response");
                return null;
            }
   
            return response.substring(startIndex, endIndex);
        } catch (Exception e) {
            logger.error("Error while extracting ObjectID: " + e.getMessage());
            return null;
        }
    }

    private String safeGetAsText(JsonNode node, String key) {
        if (node != null && node.has(key) && !node.get(key).isNull()) {
            return node.get(key).asText();
        }
        return null;
    }
}