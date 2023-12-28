package com.eitanmedical.app.bydexporter.boundries;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SerialNumber {
    
    @JsonProperty("ID")
    private String id;

    public SerialNumber() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SerialNumber [id=" + id + "]";
    }

    

    

    
}
