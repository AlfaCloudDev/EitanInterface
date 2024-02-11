package com.eitanmedical.app.bydimporter.inbounddelivery.boundries;

public class InBoundFileNamePostBYDDto {
    private String fileName;
    private FileDestination destination;

    public enum FileDestination {
        SUCCESS, ERROR
    }

    // Getters and Setters
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileDestination getDestination() {
        return destination;
    }

    public void setDestination(FileDestination destination) {
        this.destination = destination;
    }
}