package com.eitanmedical.app.bydimporter.outbounddelivery.services;

import java.io.IOException;

public interface FileProcessingInterface {
    String processAllFilesAndSendToByD() throws IOException;
    void finalizeFileProcessing(String fileName) throws IOException;
    void createLog(String logContents) throws IOException;
    
}
