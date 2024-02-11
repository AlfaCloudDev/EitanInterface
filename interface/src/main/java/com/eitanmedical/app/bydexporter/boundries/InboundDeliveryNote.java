package com.eitanmedical.app.bydexporter.boundries;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InboundDeliveryNote {
    

    @JsonProperty("WarehouseRequestID")
    private String warehouseRequestID;
    @JsonProperty("TrackingNumber")
    private String trackingNumber;
    @JsonProperty("ShipToPartyID")
    private String shipToPartyID;
    @JsonProperty("ShipToPartyName")
    private String shipToPartyName;
    @JsonProperty("ShipToLocationID")
    private String shipToLocationID; 
    @JsonProperty("ShipToLocationName")
    private String shipToLocationName;
    @JsonProperty("ShipToLocationStreet")
    private String shipToLocationStreet;
    @JsonProperty("ShipToLocationCity")
    private String shipToLocationCity;
    @JsonProperty("ShipToLocationState")
    private String shipToLocationState;
    @JsonProperty("ShipToLocationZip")
    private String shipToLocationZip;
    @JsonProperty("ShipToLocationCountry")
    private String shipToLocationCountry;
    @JsonProperty("SupplierID")
    private String supplierID;
    @JsonProperty("SupplierName")
    private String supplierName;
    @JsonProperty("FreightForwarder")
    private String freightForwarder;
    @JsonProperty("Items")
    private List<InboundDeliveryNoteItem> items;



    public InboundDeliveryNote() {
    }



    public String getWarehouseRequestID() {
        return warehouseRequestID;
    }



    public void setWarehouseRequestID(String warehouseRequestID) {
        this.warehouseRequestID = warehouseRequestID;
    }



    public String getTrackingNumber() {
        return trackingNumber;
    }



    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }



    public String getShipToPartyID() {
        return shipToPartyID;
    }



    public void setShipToPartyID(String shipToPartyID) {
        this.shipToPartyID = shipToPartyID;
    }



    public String getShipToPartyName() {
        return shipToPartyName;
    }



    public void setShipToPartyName(String shipToPartyName) {
        this.shipToPartyName = shipToPartyName;
    }



    public String getShipToLocationID() {
        return shipToLocationID;
    }



    public void setShipToLocationID(String shipToLocationID) {
        this.shipToLocationID = shipToLocationID;
    }



    public String getShipToLocationName() {
        return shipToLocationName;
    }



    public void setShipToLocationName(String shipToLocationName) {
        this.shipToLocationName = shipToLocationName;
    }



    public String getShipToLocationStreet() {
        return shipToLocationStreet;
    }



    public void setShipToLocationStreet(String shipToLocationStreet) {
        this.shipToLocationStreet = shipToLocationStreet;
    }



    public String getShipToLocationCity() {
        return shipToLocationCity;
    }



    public void setShipToLocationCity(String shipToLocationCity) {
        this.shipToLocationCity = shipToLocationCity;
    }



    public String getShipToLocationState() {
        return shipToLocationState;
    }



    public void setShipToLocationState(String shipToLocationState) {
        this.shipToLocationState = shipToLocationState;
    }



    public String getShipToLocationZip() {
        return shipToLocationZip;
    }



    public void setShipToLocationZip(String shipToLocationZip) {
        this.shipToLocationZip = shipToLocationZip;
    }



    public String getShipToLocationCountry() {
        return shipToLocationCountry;
    }



    public void setShipToLocationCountry(String shipToLocationCountry) {
        this.shipToLocationCountry = shipToLocationCountry;
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



    public String getFreightForwarder() {
        return freightForwarder;
    }



    public void setFreightForwarder(String freightForwarder) {
        this.freightForwarder = freightForwarder;
    }



    public List<InboundDeliveryNoteItem> getItems() {
        return items;
    }



    public void setItems(List<InboundDeliveryNoteItem> items) {
        this.items = items;
    }



    @Override
    public String toString() {
        return "InboundDeliveryNote [warehouseRequestID=" + warehouseRequestID + ", trackingNumber=" + trackingNumber
                + ", shipToPartyID=" + shipToPartyID + ", shipToPartyName=" + shipToPartyName + ", shipToLocationID="
                + shipToLocationID + ", shipToLocationName=" + shipToLocationName + ", shipToLocationStreet="
                + shipToLocationStreet + ", shipToLocationCity=" + shipToLocationCity + ", shipToLocationState="
                + shipToLocationState + ", shipToLocationZip=" + shipToLocationZip + ", shipToLocationCountry="
                + shipToLocationCountry + ", supplierID=" + supplierID + ", supplierName=" + supplierName
                + ", freightForwarder=" + freightForwarder + ", items=" + items + "]";
    }

    

    
}