package com.eitanmedical.app.bydimporter.services;

import com.eitanmedical.app.bydimporter.boundries.OutboundFTPFileDto;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

@Service
public class DatabaseValidationService {
    private final ByDODataService byDODataService;

    public DatabaseValidationService(ByDODataService byDODataService) {
        this.byDODataService = byDODataService;
    }

    public List<String> validateFields(OutboundFTPFileDto fileDto) {
        List<String> errorMessages = new ArrayList<>();

        for (OutboundFTPFileDto.ItemData item : fileDto.getItems()) {
            if ("TEST".equalsIgnoreCase(item.getLotCode())) {
                continue;
            }
            // Validate lot codes
            if (!isValidInDatabase(item.getLotCode(), "https://my353793.sapbydesign.com/sap/byd/odata/cust/v1/outboundtest/IdentifiedStockCollection")) {
                errorMessages.add("Invalid lot code: " + item.getLotCode());
            }
            // // Validate product IDs
            // if (!isValidInDatabase(item.getProductID(), "https://example.com/productCollection")) {
            //     errorMessages.add("Invalid product ID: " + item.getProductID());
            // }
            // // Validate serial numbers
            // if (item.getSerialNumbers() != null) {
            //     for (OutboundFTPFileDto.SerialNumberDto serial : item.getSerialNumbers()) {
            //         if (!isValidInDatabase(serial.getSerialNumber(), "https://example.com/serialNumberCollection")) {
            //             errorMessages.add("Invalid serial number: " + serial.getSerialNumber());
            //         }
            //     }
            // }
        }
        return errorMessages;
    }

    private boolean isValidInDatabase(String fieldValue, String uriTemplate) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            return false;
        }
            String encodedFieldValue = UriUtils.encode(fieldValue, StandardCharsets.UTF_8.toString());
            String uri = uriTemplate + "?$filter=ID eq '" + encodedFieldValue + "'";
            System.out.println("Encoded URI: " + uri);
            String response = byDODataService.sendGetRequestToByD(uri);
            return response.contains(fieldValue);

    }
}