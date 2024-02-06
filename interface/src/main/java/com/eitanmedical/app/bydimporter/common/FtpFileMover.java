package com.eitanmedical.app.bydimporter.common;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class FtpFileMover {

    @Autowired
    private FtpConnectionManager connectionManager;

    public boolean moveFile(String sourceFilePath, String destinationFilePath) throws IOException {
        FTPClient ftpClient = connectionManager.openConnection();
        try {
            return ftpClient.rename(sourceFilePath, destinationFilePath);
        } finally {
            connectionManager.closeConnection(ftpClient);
        }
    }
}