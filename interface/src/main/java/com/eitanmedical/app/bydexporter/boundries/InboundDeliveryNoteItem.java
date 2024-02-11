package com.eitanmedical.app.bydexporter.boundries;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InboundDeliveryNoteItem {
    @JsonProperty("LineItemID")
    private String lineItemID;
    @JsonProperty("PurchaseOrderID")
    private String purchaseOrderID;
    @JsonProperty("ProductID")
    private String productID; 
    @JsonProperty("Quantity")
    private int quantity;
    @JsonProperty("QuantityUOM")
    private String quantityUOM;
    @JsonProperty("SerialNumbers")
    private List<String> serialNumbers;
    @JsonProperty("IdentificationStockID")
    private String IdentificationStockID;


    public InboundDeliveryNoteItem() {
    }


    public String getLineItemID() {
        return lineItemID;
    }


    public void setLineItemID(String lineItemID) {
        this.lineItemID = lineItemID;
    }


    public String getPurchaseOrderID() {
        return purchaseOrderID;
    }


    public void setPurchaseOrderID(String purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }


    public String getProductID() {
        return productID;
    }


    public void setProductID(String productID) {
        this.productID = productID;
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


    public List<String> getSerialNumbers() {
        return serialNumbers;
    }


    public void setSerialNumbers(List<String> serialNumbers) {
        this.serialNumbers = serialNumbers;
    }


    public String getIdentificationStockID() {
        return IdentificationStockID;
    }


    public void setIdentificationStockID(String identificationStockID) {
        IdentificationStockID = identificationStockID;
    }


    @Override
    public String toString() {
        return "InboundDeliveryNoteItem [lineItemID=" + lineItemID + ", purchaseOrderID=" + purchaseOrderID
                + ", productID=" + productID + ", quantity=" + quantity + ", quantityUOM=" + quantityUOM
                + ", serialNumbers=" + serialNumbers + ", IdentificationStockID=" + IdentificationStockID + "]";
    }

    


    
}
    