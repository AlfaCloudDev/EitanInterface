package com.example.demo.boundries;

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
    private String ShipFromAccountID;
    @JsonProperty("ShipFromAccountName")
    private String ShipFromAccountName;
    @JsonProperty("ShipFromAddressStreet")
    private String ShipFromAddressStreet;
    @JsonProperty("ShipFromAddressCity")
    private String ShipFromAddressCity;
    @JsonProperty("ShipFromAddressState")
    private String ShipFromAddressState;
    @JsonProperty("ShipFromAddressZip")
    private String ShipFromAddressZip;
    @JsonProperty("ShipFromAddressCountry")
    private String ShipFromAddressCountry;
    @JsonProperty("ShipToID")
    private String shipToID;
    @JsonProperty("AdvisedInboundDelivreyNotification")
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



    public String getAdvisedInboundDeliveyNotification() {
        return advisedInboundDeliveryNotification;
    }



    public void setAdvisedInboundDeliveyNotification(String advisedInboundDeliveryNotification) {
        this.advisedInboundDeliveryNotification = advisedInboundDeliveryNotification;
    }



    public String getShipFromAccountID() {
        return ShipFromAccountID;
    }



    public void setShipFromAccountID(String shipFromAccountID) {
        ShipFromAccountID = shipFromAccountID;
    }



    public String getShipFromAccountName() {
        return ShipFromAccountName;
    }



    public void setShipFromAccountName(String shipFromAccountName) {
        ShipFromAccountName = shipFromAccountName;
    }



    public String getShipFromAddressStreet() {
        return ShipFromAddressStreet;
    }



    public void setShipFromAddressStreet(String shipFromAddressStreet) {
        ShipFromAddressStreet = shipFromAddressStreet;
    }



    public String getShipFromAddressCity() {
        return ShipFromAddressCity;
    }



    public void setShipFromAddressCity(String shipFromAddressCity) {
        ShipFromAddressCity = shipFromAddressCity;
    }



    public String getShipFromAddressState() {
        return ShipFromAddressState;
    }



    public void setShipFromAddressState(String shipFromAddressState) {
        ShipFromAddressState = shipFromAddressState;
    }



    public String getShipFromAddressZip() {
        return ShipFromAddressZip;
    }



    public void setShipFromAddressZip(String shipFromAddressZip) {
        ShipFromAddressZip = shipFromAddressZip;
    }



    public String getShipFromAddressCountry() {
        return ShipFromAddressCountry;
    }



    public void setShipFromAddressCountry(String shipFromAddressCountry) {
        ShipFromAddressCountry = shipFromAddressCountry;
    }



    @Override
    public String toString() {
        return "GoodsReceiptProposalItem [lineItemID=" + lineItemID + ", productId=" + productId
                + ", productDescription=" + productDescription + ", openPurchaseOrderQuantity="
                + openPurchaseOrderQuantity + ", openPurchaseOrderQuantityUOM=" + openPurchaseOrderQuantityUOM
                + ", purchaseOrderDeliveryDate=" + purchaseOrderDeliveryDate + ", deliverySchedule=" + deliverySchedule
                + ", advised=" + advised + ", deliveryCompleted=" + deliveryCompleted + ", canceled=" + canceled
                + ", ShipFromAccountID=" + ShipFromAccountID + ", ShipFromAccountName=" + ShipFromAccountName
                + ", ShipFromAddressStreet=" + ShipFromAddressStreet + ", ShipFromAddressCity=" + ShipFromAddressCity
                + ", ShipFromAddressState=" + ShipFromAddressState + ", ShipFromAddressZip=" + ShipFromAddressZip
                + ", ShipFromAddressCountry=" + ShipFromAddressCountry + ", shipToID=" + shipToID
                + ", advisedInboundDeliveyNotification=" + advisedInboundDeliveryNotification + "]";
    }

    

    



    

    
    
    


}
