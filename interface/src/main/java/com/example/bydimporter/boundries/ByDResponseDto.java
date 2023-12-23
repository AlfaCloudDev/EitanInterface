package com.example.bydimporter.boundries;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ByDResponseDto {
    
    @JsonProperty("ObjectID")
    private String objectID;

    //GETTERS SETTERS
    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

}