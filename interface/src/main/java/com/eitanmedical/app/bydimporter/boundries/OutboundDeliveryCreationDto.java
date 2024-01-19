package com.eitanmedical.app.bydimporter.boundries;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OutboundDeliveryCreationDto {

    @JsonProperty("SalesOrderID")
    private String salesOrderID;

    @JsonProperty("ShipFromSite")
    private String shipFromSite;

    @JsonProperty("FTPFileName") 
    private String fileName;

    @JsonProperty("OutboundDeliveryCreationItem")
    private List<OutboundDeliveryCreationItem> outboundDeliveryCreationItems;

    @JsonProperty("UniqueRequestID")
    private String uniquRequestID;

    @JsonProperty("trackingNumber")
    private String trackingNumber;
    
    @JsonProperty("internalComment")
    private String internalComment;
    
    // Constructors
    public OutboundDeliveryCreationDto() {}

    public OutboundDeliveryCreationDto(String salesOrderID, String shipFromSite, String trackingNumber, List<OutboundDeliveryCreationItem> items) {
        this.salesOrderID = salesOrderID;
        this.shipFromSite = shipFromSite;
        this.trackingNumber = trackingNumber;
        this.outboundDeliveryCreationItems = items;
    }

    // Getters and Setters
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getSalesOrderID() {
        return salesOrderID;
    }

    public void setSalesOrderID(String salesOrderID) {
        this.salesOrderID = salesOrderID;
    }

    public String getShipFromSite() {
        return shipFromSite;
    }

    public void setShipFromSite(String shipFromSite) {
        this.shipFromSite = shipFromSite;
    }

    public List<OutboundDeliveryCreationItem> getOutboundDeliveryCreationItems() {
        return outboundDeliveryCreationItems;
    }

    public void setOutboundDeliveryCreationItems(List<OutboundDeliveryCreationItem> items) {
        this.outboundDeliveryCreationItems = items;
    }

    public String getUniquRequestID() {
        return uniquRequestID;
    }

    public void setUniquRequestID(String uniquRequestID) {
        this.uniquRequestID = uniquRequestID;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public String getInternalComment() {
        return internalComment;
    }

    public void setInternalComment(String internalComment) {
        this.internalComment = internalComment;
    }

    // Inner class OutboundDeliveryCreationItem
    public static class OutboundDeliveryCreationItem {
        @JsonProperty("LineItem")
        private String lineItem;

        @JsonProperty("ProductID")
        private String productID;

        @JsonProperty("ActualQuantity")
        private String actualQuantity;

        @JsonProperty("IdentifiedStock")
        private String identifiedStock;

        @JsonProperty("OutboundDeliveryCreationSerial")
        private List<OutboundDeliveryCreationSerial> outboundDeliveryCreationSerials;


        // Constructors
        public OutboundDeliveryCreationItem() {}

        public OutboundDeliveryCreationItem(String lineItem, String productID, String actualQuantity, String identifiedStock, List<OutboundDeliveryCreationSerial> serials) {
            this.lineItem = lineItem;
            this.productID = productID;
            this.actualQuantity = actualQuantity;
            this.identifiedStock = identifiedStock;
            this.outboundDeliveryCreationSerials = serials;
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

        public List<OutboundDeliveryCreationSerial> getOutboundDeliveryCreationSerials() {
            return outboundDeliveryCreationSerials;
        }

        public void setOutboundDeliveryCreationSerials(List<OutboundDeliveryCreationSerial> serials) {
            this.outboundDeliveryCreationSerials = serials;
        }

    }

    // Inner class OutboundDeliveryCreationSerial
    public static class OutboundDeliveryCreationSerial {
        @JsonProperty("SerialID")
        private String serialID;

        // Constructors
        public OutboundDeliveryCreationSerial() {}

        public OutboundDeliveryCreationSerial(String serialID) {
            this.serialID = serialID;
        }

        // Getters and Setters
        public String getSerialID() {
            return serialID;
        }

        public void setSerialID(String serialID) {
            this.serialID = serialID;
        }

    }



}