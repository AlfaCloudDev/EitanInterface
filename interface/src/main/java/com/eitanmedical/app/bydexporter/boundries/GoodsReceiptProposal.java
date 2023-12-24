package com.eitanmedical.app.bydexporter.boundries;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoodsReceiptProposal {

    @JsonProperty("ID")
    private String id; 
    @JsonProperty("Status")
    private String status;
    @JsonProperty("SupplierID")
    private String supplierID;
    @JsonProperty("SupplierName")
    private String supplierName;
    @JsonProperty("SiteID")
    private String siteID;
    @JsonProperty("PurchaseOrderDeliveryDate")
    private String purchaseOrderDeliveryDate;
    @JsonProperty("Item")
    private List<GoodsReceiptProposalItem> items;


    public GoodsReceiptProposal() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getPurchaseOrderDeliveryDate() {
        return purchaseOrderDeliveryDate;
    }

    public void setPurchaseOrderDeliveryDate(String purchaseOrderDeliveryDate) {
        this.purchaseOrderDeliveryDate = purchaseOrderDeliveryDate;
    }

    public List<GoodsReceiptProposalItem> getItems() {
        return items;
    }

    public void setItems(List<GoodsReceiptProposalItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "GoodsReceiptProposal [id=" + id + ", status=" + status + ", supplierID=" + supplierID
                + ", supplierName=" + supplierName + ", siteID=" + siteID + ", purchaseOrderDeliveryDate="
                + purchaseOrderDeliveryDate + ", items=" + items + "]";
    }

    

    

  
}
