package com.eitanmedical.app.bydimporter.inbounddelivery.services;

import java.io.IOException;

import com.eitanmedical.app.bydimporter.inbounddelivery.boundries.InBoundFileNamePostBYDDto;

public interface FileProcessingInterface {
    String processAllFilesAndSendToByD() throws IOException;
    void finalizeFileProcessing(String fileName, InBoundFileNamePostBYDDto.FileDestination destination) throws IOException;
    void createLog(String logContents) throws IOException;
}
