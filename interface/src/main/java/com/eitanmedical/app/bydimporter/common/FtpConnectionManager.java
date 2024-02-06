package com.eitanmedical.app.bydimporter.common;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class FtpConnectionManager {

    @Value("${EITAN_INTERFACE_FTP_PASSWORD:rhcl1234}")
    private String ftpPassword;

    @Value("${EITAN_INTERFACE_FTP_USERNAME:rhcleitan}")
    private String ftpUser;

    @Value("${EITAN_INTERFACE_FTP_SERVER:ftp.drivehq.com}")
    private String ftpServer;

    @Value("${EITAN_INTERFACE_FTP_PORT:21}")
    private int ftpPort;

    public FTPClient openConnection() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftpServer, ftpPort);
        ftpClient.login(ftpUser, ftpPassword);
        ftpClient.enterLocalPassiveMode();
        return ftpClient;
    }

    public void closeConnection(FTPClient ftpClient) throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
}