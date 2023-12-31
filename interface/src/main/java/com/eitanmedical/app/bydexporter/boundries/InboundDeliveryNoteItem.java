package com.eitanmedical.app.bydexporter.boundries;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InboundDeliveryNoteItem {
    @JsonProperty("LineItemID")
    private String lineItemID;
    @JsonProperty("Reference")
    private String reference;
    @JsonProperty("ProductID")
    private String productId; 
    @JsonProperty("ProductDescription")
    private String productDescription;
    @JsonProperty("Quantity")
    private int quantity;
    @JsonProperty("QuantityUOM")
    private String quantityUOM;
    @JsonProperty("SerialNumbers")
    private List<SerialNumber> serialNumbers;


    public InboundDeliveryNoteItem() {
    }


    public String getLineItemID() {
        return lineItemID;
    }


    public void setLineItemID(String lineItemID) {
        this.lineItemID = lineItemID;
    }


    public String getReference() {
        return reference;
    }


    public void setReference(String reference) {
        this.reference = reference;
    }


    public String getProductId() {
        return productId;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getProductDescription() {
        return productDescription;
    }


    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String getQuantityUOM() {
        return quantityUOM;
    }


    public void setQuantityUOM(String quantityUOM) {
        this.quantityUOM = quantityUOM;
    }


    public List<SerialNumber> getSerialNumbers() {
        return serialNumbers;
    }


    public void setSerialNumbers(List<SerialNumber> serialNumbers) {
        this.serialNumbers = serialNumbers;
    }

    @Override
    public String toString() {
        return "InboundDeliveryNoteItem [lineItemID=" + lineItemID + ", reference=" + reference + ", productId="
                + productId + ", productDescription=" + productDescription + ", quantity=" + quantity + ", quantityUOM="
                + quantityUOM + ", SerialNumbers=" + serialNumbers + "]";
    }

    

    

}
    