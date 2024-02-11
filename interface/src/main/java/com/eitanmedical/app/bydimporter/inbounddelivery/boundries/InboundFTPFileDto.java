package com.eitanmedical.app.bydimporter.inbounddelivery.boundries;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InboundFTPFileDto {
    @JsonProperty("ID")
    private String id;

    @JsonProperty("SiteID")
    private String siteID;

    @JsonProperty("Item")
    private List<ItemData> items;
    
    //GETTERS SETTERS
    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }


    public static class ItemData {
        @JsonProperty("ProductID")
        private String productID;

        @JsonProperty("LineItem")
        private String lineItem;

        @JsonProperty("OpenPurchaseOrderQuantity")
        private Double quantity;

        @JsonProperty("LotCode")
        private String lotcode;

        @JsonProperty("Status")
        private String status;


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @JsonProperty("SerialNumbers")
        private List<SerialNumberDto> serialNumbers;

        //GETTERS SETTERS

        public String getProductID() {
            return productID;
        }
    
        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getLotCode() {
            return lotcode;
        }
    
        public void setLotCode(String lotcode) {
            this.lotcode = lotcode;
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

        @JsonProperty("Staus")
        private String status;

        //GETTERS SETTERS
        
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

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

