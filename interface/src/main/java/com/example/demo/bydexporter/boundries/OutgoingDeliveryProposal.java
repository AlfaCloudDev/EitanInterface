package com.example.demo.bydexporter.boundries;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OutgoingDeliveryProposal {
    @JsonProperty("OrderDate")
    private String orderDate; 
    @JsonProperty("Reference")
    private String reference;
    @JsonProperty("ExternalReference")
    private String externalReference;
    @JsonProperty("ShippingMethod")
    private String shippingMethod;
    @JsonProperty("CustomerFreightPayment")
    private boolean customerFreightPayment;
    @JsonProperty("ShipFromSite")
    private String shipFromSite;
    @JsonProperty("ShipFromLocation")
    private String shipFromLocation;
    @JsonProperty("AccountID")
    private String accountID;
    @JsonProperty("AccountName")
    private String accountName;
    @JsonProperty("Item")
    private List<OutgoingDeliveryProposalItem> items;
    
    public OutgoingDeliveryProposal(){

    }

    

    public String getOrderDate() {
        return orderDate;
    }



    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }



    public String getReference() {
        return reference;
    }



    public void setReference(String reference) {
        this.reference = reference;
    }



    public String getExternalReference() {
        return externalReference;
    }



    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }



    public String getShippingMethod() {
        return shippingMethod;
    }



    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }



    public boolean getCustomerFreightPayment() {
        return customerFreightPayment;
    }



    public void setCustomerFreightPayment(boolean customerFreightPayment) {
        this.customerFreightPayment = customerFreightPayment;
    }



    public String getShipFromSite() {
        return shipFromSite;
    }



    public void setShipFromSite(String shipFromSite) {
        this.shipFromSite = shipFromSite;
    }



    public String getShipFromLocation() {
        return shipFromLocation;
    }



    public void setShipFromLocation(String shipFromLocation) {
        this.shipFromLocation = shipFromLocation;
    }



    public String getAccountID() {
        return accountID;
    }



    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }



    public String getAccountName() {
        return accountName;
    }



    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public List<OutgoingDeliveryProposalItem> getItems() {
        return items;
    }

    public void setItems(List<OutgoingDeliveryProposalItem> items) {
        this.items = items;
    }



    @Override
    public String toString() {
        return "OutgoingDeliveryProposal [orderDate=" + orderDate + ", reference=" + reference + ", externalReference="
                + externalReference + ", shippingMethod=" + shippingMethod + ", customerFreightPayment="
                + customerFreightPayment + ", shipFromSite=" + shipFromSite + ", shipFromLocation=" + shipFromLocation
                + ", accountID=" + accountID + ", accountName=" + accountName + ", items=" + items + "]";
    }



    



    

    
    
}