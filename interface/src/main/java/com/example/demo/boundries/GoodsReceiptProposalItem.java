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
    @JsonProperty("ShipToID")
    private String shipToID;
    @JsonProperty("AdvisedInboundDeliveyNotification")
    private String advisedInboundDeliveyNotification;



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
        return advisedInboundDeliveyNotification;
    }



    public void setAdvisedInboundDeliveyNotification(String advisedInboundDeliveyNotification) {
        this.advisedInboundDeliveyNotification = advisedInboundDeliveyNotification;
    }



    @Override
    public String toString() {
        return "GoodsReceiptProposalItem [lineItemID=" + lineItemID + ", productId=" + productId
                + ", productDescription=" + productDescription + ", openPurchaseOrderQuantity="
                + openPurchaseOrderQuantity + ", openPurchaseOrderQuantityUOM=" + openPurchaseOrderQuantityUOM
                + ", purchaseOrderDeliveryDate=" + purchaseOrderDeliveryDate + ", deliverySchedule=" + deliverySchedule
                + ", advised=" + advised + ", deliveryCompleted=" + deliveryCompleted + ", canceled=" + canceled
                + ", shipToID=" + shipToID + ", advisedInboundDeliveyNotification=" + advisedInboundDeliveyNotification
                + "]";
    }



    

    
    
    


}
