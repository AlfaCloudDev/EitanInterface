package com.example.demo.boundries;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OutgoingDeliveryProposalItem {
    
    @JsonProperty("LineItem")
    private String lineItem;
    @JsonProperty("ProductID")
    private String productId;
    @JsonProperty("ShipToParty")
    private String shipToParty;
    @JsonProperty("Quantity")
    private int quantity;
    @JsonProperty("UOM")
    private String uom;
    @JsonProperty("ShipToAddressStreet")
    private String shipToAddressStreet;
    @JsonProperty("ShipToAddressCity")
    private String shipToAddressCity;
    @JsonProperty("ShipToAddressState")
    private String shipToAddressState;
    @JsonProperty("ShipToAddressZip")
    private String shipToAddressZip;
    @JsonProperty("ShipToAddressCountry")
    private String shipToAddressCountry;
    @JsonProperty("Carrier")
    private String carrier;
    @JsonProperty("ServiceType")
    private String serviceType;
    @JsonProperty("DeliveryRule")
    private String deliveryRule;
    @JsonProperty("ShippingCondition")
    private String shippingCondition;



    public OutgoingDeliveryProposalItem(){
       
    }



    public String getLineItem() {
        return lineItem;
    }



    public void setLineItem(String lineItem) {
        this.lineItem = lineItem;
    }



    public String getProductId() {
        return productId;
    }



    public void setProductId(String productId) {
        this.productId = productId;
    }



    public String getShipToParty() {
        return shipToParty;
    }



    public void setShipToParty(String shipToParty) {
        this.shipToParty = shipToParty;
    }



    public int getQuantity() {
        return quantity;
    }



    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public String getUom() {
        return uom;
    }



    public void setUom(String uom) {
        this.uom = uom;
    }



    public String getShipToAddressStreet() {
        return shipToAddressStreet;
    }



    public void setShipToAddressStreet(String shipToAddressStreet) {
        this.shipToAddressStreet = shipToAddressStreet;
    }



    public String getShipToAddressCity() {
        return shipToAddressCity;
    }



    public void setShipToAddressCity(String shipToAddressCity) {
        this.shipToAddressCity = shipToAddressCity;
    }



    public String getShipToAddressState() {
        return shipToAddressState;
    }



    public void setShipToAddressState(String shipToAddressState) {
        this.shipToAddressState = shipToAddressState;
    }



    public String getShipToAddressZip() {
        return shipToAddressZip;
    }



    public void setShipToAddressZip(String shipToAddressZip) {
        this.shipToAddressZip = shipToAddressZip;
    }



    public String getShipToAddressCountry() {
        return shipToAddressCountry;
    }



    public void setShipToAddressCountry(String shipToAddressCountry) {
        this.shipToAddressCountry = shipToAddressCountry;
    }



    public String getCarrier() {
        return carrier;
    }



    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }



    public String getServiceType() {
        return serviceType;
    }



    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }



    public String getDeliveryRule() {
        return deliveryRule;
    }



    public void setDeliveryRule(String deliveryRule) {
        this.deliveryRule = deliveryRule;
    }



    public String getShippingCondition() {
        return shippingCondition;
    }



    public void setShippingCondition(String shippingCondition) {
        this.shippingCondition = shippingCondition;
    }



    @Override
    public String toString() {
        return "OutgoingDeliveryProposalItem [lineItem=" + lineItem + ", productId=" + productId + ", shipToParty="
                + shipToParty + ", quantity=" + quantity + ", uom=" + uom + ", shipToAddressStreet="
                + shipToAddressStreet + ", shipToAddressCity=" + shipToAddressCity + ", shipToAddressState="
                + shipToAddressState + ", shipToAddressZip=" + shipToAddressZip + ", shipToAddressCountry="
                + shipToAddressCountry + ", carrier=" + carrier + ", serviceType=" + serviceType + ", deliveryRule="
                + deliveryRule + ", shippingCondition=" + shippingCondition + "]";
    }
}