package com.eitanmedical.app.bydimporter.outbounddelivery.services;

import com.eitanmedical.app.bydimporter.outbounddelivery.boundries.OutBoundFileNamePostBYDDto;
import java.io.IOException;

public interface FileProcessingInterface {
    String processAllFilesAndSendToByD() throws IOException;
    void finalizeFileProcessing(String fileName, OutBoundFileNamePostBYDDto.FileDestination destination) throws IOException;
    void createLog(String logContents) throws IOException;
}