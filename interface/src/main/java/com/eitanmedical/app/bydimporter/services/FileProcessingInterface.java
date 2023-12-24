package com.eitanmedical.app.bydimporter.services;

import java.io.IOException;

public interface FileProcessingInterface {
    String processAllFilesAndSendToByD() throws IOException;
    void finalizeFileProcessing(String fileName) throws IOException;
}
