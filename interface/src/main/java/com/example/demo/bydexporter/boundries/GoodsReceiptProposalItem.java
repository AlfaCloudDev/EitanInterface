package com.example.demo.bydexporter.boundries;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoodsReceiptProposalItem {
    
    @JsonProperty("LineItemID")
    private String lineItemID;
    @JsonProperty("ProductID")
    private String productId;
    @JsonProperty("ProductDescription")
    private String productDescription;
    @JsonProperty("OpenPurchaseOrderQuantity")
    private int openPurchaseOrderQuantity;
    @JsonProperty("OpenPurchaseOrderQuantityUOM")
    private String openPurchaseOrderQuantityUOM;
    @JsonProperty("PurchaseOrderDeliveryDate")
    private String purchaseOrderDeliveryDate;
    @JsonProperty("DeliverySchedule")
    private String deliverySchedule;
    @JsonProperty("Advised")
    private String advised;
    @JsonProperty("DeliveryCompleted")
    private String deliveryCompleted;
    @JsonProperty("Canceled")
    private String canceled;
    @JsonProperty("ShipFromAccountID")
    private String shipFromAccountID;
    @JsonProperty("ShipFromAccountName")
    private String shipFromAccountName;
    @JsonProperty("ShipFromAddressStreet")
    private String shipFromAddressStreet;
    @JsonProperty("ShipFromAddressCity")
    private String shipFromAddressCity;
    @JsonProperty("ShipFromAddressState")
    private String shipFromAddressState;
    @JsonProperty("ShipFromAddressZip")
    private String shipFromAddressZip;
    @JsonProperty("ShipFromAddressCountry")
    private String shipFromAddressCountry;
    @JsonProperty("ShipToID")
    private String shipToID;
    @JsonProperty("AdvisedInboundDeliveryNotification")
    private String advisedInboundDeliveryNotification;



    public GoodsReceiptProposalItem() {
    }



    public String getLineItemID() {
        return lineItemID;
    }



    public void setLineItemID(String lineItemID) {
        this.lineItemID = lineItemID;
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



    public int getOpenPurchaseOrderQuantity() {
        return openPurchaseOrderQuantity;
    }



    public void setOpenPurchaseOrderQuantity(int openPurchaseOrderQuantity) {
        this.openPurchaseOrderQuantity = openPurchaseOrderQuantity;
    }



    public String getOpenPurchaseOrderQuantityUOM() {
        return openPurchaseOrderQuantityUOM;
    }



    public void setOpenPurchaseOrderQuantityUOM(String openPurchaseOrderQuantityUOM) {
        this.openPurchaseOrderQuantityUOM = openPurchaseOrderQuantityUOM;
    }



    public String getPurchaseOrderDeliveryDate() {
        return purchaseOrderDeliveryDate;
    }



    public void setPurchaseOrderDeliveryDate(String purchaseOrderDeliveryDate) {
        this.purchaseOrderDeliveryDate = purchaseOrderDeliveryDate;
    }



    public String getDeliverySchedule() {
        return deliverySchedule;
    }



    public void setDeliverySchedule(String deliverySchedule) {
        this.deliverySchedule = deliverySchedule;
    }



    public String getAdvised() {
        return advised;
    }



    public void setAdvised(String advised) {
        this.advised = advised;
    }



    public String getDeliveryCompleted() {
        return deliveryCompleted;
    }



    public void setDeliveryCompleted(String deliveryCompleted) {
        this.deliveryCompleted = deliveryCompleted;
    }



    public String getCanceled() {
        return canceled;
    }



    public void setCanceled(String canceled) {
        this.canceled = canceled;
    }



    public String getShipToID() {
        return shipToID;
    }



    public void setShipToID(String shipToID) {
        this.shipToID = shipToID;
    }


    public String getShipFromAccountID() {
        return shipFromAccountID;
    }



    public void setShipFromAccountID(String shipFromAccountID) {
        this.shipFromAccountID = shipFromAccountID;
    }



    public String getShipFromAccountName() {
        return shipFromAccountName;
    }



    public void setShipFromAccountName(String shipFromAccountName) {
        this.shipFromAccountName = shipFromAccountName;
    }



    public String getShipFromAddressStreet() {
        return shipFromAddressStreet;
    }



    public void setShipFromAddressStreet(String shipFromAddressStreet) {
        this.shipFromAddressStreet = shipFromAddressStreet;
    }



    public String getShipFromAddressCity() {
        return shipFromAddressCity;
    }



    public void setShipFromAddressCity(String shipFromAddressCity) {
        this.shipFromAddressCity = shipFromAddressCity;
    }



    public String getShipFromAddressState() {
        return shipFromAddressState;
    }



    public void setShipFromAddressState(String shipFromAddressState) {
        this.shipFromAddressState = shipFromAddressState;
    }



    public String getShipFromAddressZip() {
        return shipFromAddressZip;
    }



    public void setShipFromAddressZip(String shipFromAddressZip) {
        this.shipFromAddressZip = shipFromAddressZip;
    }



    public String getShipFromAddressCountry() {
        return shipFromAddressCountry;
    }



    public void setShipFromAddressCountry(String shipFromAddressCountry) {
        this.shipFromAddressCountry = shipFromAddressCountry;
    }



    public String getAdvisedInboundDeliveryNotification() {
        return advisedInboundDeliveryNotification;
    }



    public void setAdvisedInboundDeliveryNotification(String advisedInboundDeliveryNotification) {
        this.advisedInboundDeliveryNotification = advisedInboundDeliveryNotification;
    }



    @Override
    public String toString() {
        return "GoodsReceiptProposalItem [lineItemID=" + lineItemID + ", productId=" + productId
                + ", productDescription=" + productDescription + ", openPurchaseOrderQuantity="
                + openPurchaseOrderQuantity + ", openPurchaseOrderQuantityUOM=" + openPurchaseOrderQuantityUOM
                + ", purchaseOrderDeliveryDate=" + purchaseOrderDeliveryDate + ", deliverySchedule=" + deliverySchedule
                + ", advised=" + advised + ", deliveryCompleted=" + deliveryCompleted + ", canceled=" + canceled
                + ", shipFromAccountID=" + shipFromAccountID + ", shipFromAccountName=" + shipFromAccountName
                + ", shipFromAddressStreet=" + shipFromAddressStreet + ", shipFromAddressCity=" + shipFromAddressCity
                + ", shipFromAddressState=" + shipFromAddressState + ", shipFromAddressZip=" + shipFromAddressZip
                + ", shipFromAddressCountry=" + shipFromAddressCountry + ", shipToID=" + shipToID
                + ", advisedInboundDeliveryNotification=" + advisedInboundDeliveryNotification + "]";
    }

    



    

    



    

    
    
    


}
