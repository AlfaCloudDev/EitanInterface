package com.eitanmedical.app.bydimporter.boundries;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FTPFileDto {
    @JsonProperty("Reference")
    private String reference;

    @JsonProperty("ShipFromSite")
    private String shipFromSite;

    @JsonProperty("Item")
    private List<ItemData> items;

    //GETTERS SETTERS

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        if (reference != null && reference.startsWith("EMU")) {
            this.reference = reference.substring(3); // Remove "EMU" prefix
        } else {
            this.reference = reference;
        }
    }

    public String getShipFromSite() {
        return shipFromSite;
    }

    public void setShipFromSite(String shipFromSite) {
        this.shipFromSite = shipFromSite;
    }

    public static class ItemData {
        @JsonProperty("ProductID")
        private String productID;

        @JsonProperty("LineItem")
        private String lineItem;

        @JsonProperty("Quantity")
        private Double quantity;

        @JsonProperty("SerialNumbers")
        private List<SerialNumberDto> serialNumbers;

        //GETTERS SETTERS

        public String getProductID() {
            return productID;
        }
    
        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getLineItem() {
            return lineItem;
        }
    
        public void setLineItem(String lineItem) {
            this.lineItem = lineItem;
        }

        public Double getQuantity() {
            return quantity;
        }
    
        public void setQuantity(Double quantity) {
            this.quantity = quantity;
        }


        public List<SerialNumberDto> getSerialNumbers() {
            return serialNumbers;
        }

        public void setSerialNumbers(List<SerialNumberDto> serialNumbers) {
            this.serialNumbers = serialNumbers;
        }
    }

    public static class SerialNumberDto {
        
        @JsonProperty("SerialNumber")
        private String serialNumber;

        //GETTERS SETTERS
        
        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }
    }

    public List<ItemData> getItems() {
        return items;
    }
}