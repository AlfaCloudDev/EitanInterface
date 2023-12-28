package com.eitanmedical.app.bydexporter.boundries;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InboundDeliveryNote {
    

    @JsonProperty("ID")
    private String id;
    @JsonProperty("ShipFromParty")
    private String shipFromParty;
    @JsonProperty("ShipFromPartyName")
    private String shipFromPartyName;
    @JsonProperty("ShipToParty")
    private String shipToParty; 
    @JsonProperty("ShipToPartyName")
    private String shipToPartyName;
    @JsonProperty("Items")
    private List<InboundDeliveryNoteItem> items;



    public InboundDeliveryNote() {
    }



    public String getId() {
        return id;
    }



    public void setId(String id) {
        this.id = id;
    }



    public String getShipFromParty() {
        return shipFromParty;
    }



    public void setShipFromParty(String shipFromParty) {
        this.shipFromParty = shipFromParty;
    }



    public String getShipFromPartyName() {
        return shipFromPartyName;
    }



    public void setShipFromPartyName(String shipFromPartyName) {
        this.shipFromPartyName = shipFromPartyName;
    }



    public String getShipToParty() {
        return shipToParty;
    }



    public void setShipToParty(String shipToParty) {
        this.shipToParty = shipToParty;
    }



    public String getShipToPartyName() {
        return shipToPartyName;
    }



    public void setShipToPartyName(String shipToPartyName) {
        this.shipToPartyName = shipToPartyName;
    }



    public List<InboundDeliveryNoteItem> getItems() {
        return items;
    }



    public void setItems(List<InboundDeliveryNoteItem> items) {
        this.items = items;
    }

    

    

    
}