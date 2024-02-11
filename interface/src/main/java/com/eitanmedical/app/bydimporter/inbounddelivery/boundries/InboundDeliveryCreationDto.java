package com.eitanmedical.app.bydimporter.inbounddelivery.boundries;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InboundDeliveryCreationDto {

    @JsonProperty("FTPFileName") 
    private String fileName;

    @JsonProperty("WarehouseRequestID")
    private String uniquRequestID;

    @JsonProperty("InboundDeliveryCreationItem")
    private List<InboundDeliveryCreationItem> InboundDeliveryCreationItems;
    
    // Constructors
    public InboundDeliveryCreationDto() {}

    public InboundDeliveryCreationDto(List<InboundDeliveryCreationItem> items) {
        this.InboundDeliveryCreationItems = items;
    }

    // Getters and Setters
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<InboundDeliveryCreationItem> getInboundDeliveryCreationItems() {
        return InboundDeliveryCreationItems;
    }

    public void setInboundDeliveryCreationItems(List<InboundDeliveryCreationItem> items) {
        this.InboundDeliveryCreationItems = items;
    }

    public String getUniquRequestID() {
        return uniquRequestID;
    }

    public void setUniquRequestID(String uniquRequestID) {
        this.uniquRequestID = uniquRequestID;
    }

    // Inner class InboundDeliveryCreationItem
    public static class InboundDeliveryCreationItem {
        @JsonProperty("LineItem")
        private String lineItem;

        @JsonProperty("ProductID")
        private String productID;

        @JsonProperty("ActualQuantity")
        private String actualQuantity;

        @JsonProperty("IdentifiedStock")
        private String identifiedStock;

        @JsonProperty("ItemStatus")
        private String itemStatus;
        

        @JsonProperty("InboundDeliveryCreationSerial")
        private List<InboundDeliveryCreationSerial> InboundDeliveryCreationSerials;


        // Constructors
        public InboundDeliveryCreationItem() {}

        public InboundDeliveryCreationItem(String lineItem, String productID, String actualQuantity, String identifiedStock, List<InboundDeliveryCreationSerial> serials) {
            this.lineItem = lineItem;
            this.productID = productID;
            this.actualQuantity = actualQuantity;
            this.identifiedStock = identifiedStock;
            this.InboundDeliveryCreationSerials = serials;
        }

        // Getters and Setters
        public String getLineItem() {
            return lineItem;
        }

        public void setLineItem(String lineItem) {
            this.lineItem = lineItem;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getItemStatus() {
            return itemStatus;
        }

        public void setItemStatus(String itemStatus) {
            this.itemStatus = itemStatus;
        }

        public String getActualQuantity() {
            return actualQuantity;
        }

        public void setActualQuantity(String actualQuantity) {
            this.actualQuantity = actualQuantity;
        }

        public String getIdentifiedStock() {
            return identifiedStock;
        }

        public void setIdentifiedStock(String identifiedStock) {
            this.identifiedStock = identifiedStock;
        }

        public List<InboundDeliveryCreationSerial> getInboundDeliveryCreationSerials() {
            return InboundDeliveryCreationSerials;
        }

        public void setInboundDeliveryCreationSerials(List<InboundDeliveryCreationSerial> serials) {
            this.InboundDeliveryCreationSerials = serials;
        }

    }

    // Inner class OutboundDeliveryCreationSerial
    public static class InboundDeliveryCreationSerial {
        @JsonProperty("SerialID")
        private String serialID;

        @JsonProperty("SerialStatus")
        private String serialStatus;

        // Constructors
        public InboundDeliveryCreationSerial() {}

        public InboundDeliveryCreationSerial(String serialID) {
            this.serialID = serialID;
        }

        // Getters and Setters
        public String getSerialID() {
            return serialID;
        }

        public void setSerialID(String serialID) {
            this.serialID = serialID;
        }

        public String getSerialStatus() {
            return serialStatus;
        }

        public void setSerialStatus(String serialStatus) {
            this.serialStatus = serialStatus;
        }
    }
}
