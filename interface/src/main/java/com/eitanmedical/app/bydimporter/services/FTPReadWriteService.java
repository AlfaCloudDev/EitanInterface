package com.eitanmedical.app.bydimporter.services;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import java.util.ArrayList;
import java.util.List;

@Service
public class FTPReadWriteService {

    @Value("${EITAN_INTERFACE_FTP_PASSWORD:rhcl1234}")
    private String ftpPassword;

    @Value("${EITAN_INTERFACE_FTP_USERNAME:rhcleitan}")
    private String ftpUser;

    @Value("${EITAN_INTERFACE_FTP_SERVER:ftp.drivehq.com}")
    private String ftpServer;

    @Value("${EITAN_INTERFACE_FTP_PORT:21}")
    private int ftpPort;

    static final String SUCCESS_DIRECTORY_PATH = "/drivehqshare/rgwoodfield/Test/OUT/DeliveryNote/Success";
    static final String ERROR_DIRECTORY_PATH = "/drivehqshare/rgwoodfield/Test/OUT/DeliveryNote/Error";
    private static final String FTP_DIRECTORY_PATH = "/drivehqshare/rgwoodfield/Test/OUT/DeliveryNote/Input";

    public static class FileContent {
        private final String content;
        private final String filePath;

        public FileContent(String content, String filePath) {
            this.content = content;
            this.filePath = filePath;
        }

        public String getContent() {
            return content;
        }

        public String getFilePath() {
            return filePath;
        }
    }

    public List<FileContent> readAllFiles() throws IOException {
        FTPClient ftpClient = new FTPClient();
        List<FileContent> fileContents = new ArrayList<>();

        try {
            ftpClient.connect(ftpServer, ftpPort);
            ftpClient.login(ftpUser, ftpPassword);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(FTP_DIRECTORY_PATH);

            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                if (!file.isFile()) {
                    continue;
                }

                String originalFilePath = FTP_DIRECTORY_PATH + "/" + file.getName();
                String errorFilePath = ERROR_DIRECTORY_PATH + "/" + file.getName();

                boolean isMoved = moveFile(originalFilePath, errorFilePath);
                if (!isMoved) {
                    System.out.println("Failed to move file. FTP Server Reply: " + ftpClient.getReplyString());
                    continue;
                }

                InputStream inputStream = ftpClient.retrieveFileStream(errorFilePath);
                String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                fileContents.add(new FileContent(content, errorFilePath));
                inputStream.close();
                ftpClient.completePendingCommand();
            }
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
        return fileContents;
    }

    public boolean moveFile(String sourceFilePath, String destinationFilePath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpServer, ftpPort);
            ftpClient.login(ftpUser, ftpPassword);
            ftpClient.enterLocalPassiveMode();

            return ftpClient.rename(sourceFilePath, destinationFilePath);
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }

    public void uploadLogFile(String logFileName, String logContent) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpServer, ftpPort);
            ftpClient.login(ftpUser, ftpPassword);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ERROR_DIRECTORY_PATH);
    
            InputStream inputStream = new ByteArrayInputStream(logContent.getBytes(StandardCharsets.UTF_8));
            boolean uploaded = ftpClient.storeFile(logFileName, inputStream);
            inputStream.close();

            if (!uploaded) {
                System.out.println("Failed to upload log file. FTP Server Reply: " + ftpClient.getReplyString());
            } else {
                System.out.println("Success. FTP Server Reply: " + ftpClient.getReplyString());
            }
        } catch (IOException ex) {
            System.err.println("Error during FTP operation: " + ex.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }
    }
}