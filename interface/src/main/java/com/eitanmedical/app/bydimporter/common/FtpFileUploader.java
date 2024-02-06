package com.eitanmedical.app.bydimporter.common;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class FtpFileUploader {

    @Autowired
    private FtpConnectionManager connectionManager;

    public boolean uploadFile(String directoryPath, String fileName, String content) throws IOException {
        FTPClient ftpClient = connectionManager.openConnection();
        try {
            ftpClient.changeWorkingDirectory(directoryPath);
            InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
            return ftpClient.storeFile(fileName, inputStream);
        } finally {
            connectionManager.closeConnection(ftpClient);
        }
    }
}