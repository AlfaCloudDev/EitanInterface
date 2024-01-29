package com.eitanmedical.app.bydimporter.outbounddelivery.services;

import com.eitanmedical.app.bydimporter.outbounddelivery.boundries.OutboundFTPFileDto;
import java.util.ArrayList;
import java.util.List;

public class FileValidationService {
    
    public static List<String> getValidationErrors(OutboundFTPFileDto fileDto) {
        List<String> errors = new ArrayList<>();

        if (fileDto.getReference() == null || fileDto.getReference().isEmpty()) {
            errors.add("Reference is missing in file");
        }
        if (fileDto.getTrackingNumbers() == null || fileDto.getTrackingNumbers().isEmpty()) {
            errors.add("Tracking number is missing in file");
        }
        if (fileDto.getItems() == null || fileDto.getItems().isEmpty()) {
            errors.add("Items are missing in file");
        } else {
            for (int i = 0; i < fileDto.getItems().size(); i++) {
                OutboundFTPFileDto.ItemData item = fileDto.getItems().get(i);
                if (item.getProductID() == null || item.getProductID().isEmpty()) {
                    errors.add("Product ID is missing in item " + (i + 1) + "in the file");
                }
                if (item.getLineItem() == null || item.getLineItem().isEmpty()) {
                    errors.add("Line Item is missing in item " + (i + 1) + "in the file");
                }
                if (item.getQuantity() == null) {
                    errors.add("Quantity is missing in item " + (i + 1) + "in the file");
                }

                // Check Serial Numbers
                if (item.getSerialNumbers() != null) {
                    for (int j = 0; j < item.getSerialNumbers().size(); j++) {
                        OutboundFTPFileDto.SerialNumberDto serial = item.getSerialNumbers().get(j);
                        if (serial.getSerialNumber() == null || serial.getSerialNumber().isEmpty()) {
                            errors.add("Serial Number is missing in item " + (i + 1) + ", serial " + (j + 1) + "in the file");
                        }
                    }
                }
            }
        }

        return errors;
    }
}