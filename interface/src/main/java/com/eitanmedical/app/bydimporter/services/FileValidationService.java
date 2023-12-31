package com.eitanmedical.app.bydimporter.services;

import com.eitanmedical.app.bydimporter.boundries.OutboundFTPFileDto;

public class FileValidationService {
    
    public static boolean isValidFile(OutboundFTPFileDto fileDto) {
        if (fileDto.getReference() == null || fileDto.getReference().isEmpty() ||
            fileDto.getShipFromSite() == null || fileDto.getShipFromSite().isEmpty() ||
            fileDto.getItems() == null || fileDto.getItems().isEmpty()) {
            return false;
        }

        for (OutboundFTPFileDto.ItemData item : fileDto.getItems()) {
            if (item.getProductID() == null || item.getProductID().isEmpty() ||
                item.getLineItem() == null || item.getLineItem().isEmpty() ||
                item.getQuantity() == null) {
                return false;
            }

            // Check if Serial Numbers exist and are valid
            if (item.getSerialNumbers() != null && !item.getSerialNumbers().isEmpty()) {
                for (OutboundFTPFileDto.SerialNumberDto serial : item.getSerialNumbers()) {
                    if (serial.getSerialNumber() == null || serial.getSerialNumber().isEmpty()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}