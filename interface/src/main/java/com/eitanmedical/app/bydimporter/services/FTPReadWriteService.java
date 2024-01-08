package com.eitanmedical.app.bydimporter.services;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
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

    public List<FileContent> readAllFiles(String server, String user, String password, int port, String directoryPath, String errorDirectoryPath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        List<FileContent> fileContents = new ArrayList<>();
    
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(directoryPath);
    
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                if (!file.isFile()) {
                    continue;
                }
            
                String originalFilePath = directoryPath + "/" + file.getName();
                String errorFilePath = errorDirectoryPath + "/" + file.getName();
            
                // Attempt to move the file to the error directory
                boolean isRenamed = ftpClient.rename(originalFilePath, errorFilePath);
                if (!isRenamed) {
                    System.out.println("Failed to move file. FTP Server Reply: " + ftpClient.getReplyString());
                    continue; // Skip processing this file
                }
                
                // Read the file content from the error directory
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

    public void deleteFile(String server, String user, String password, int port, String filePath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
    
            boolean deleted = ftpClient.deleteFile(filePath);
    
            if (!deleted) {
                System.out.println("Failed to delete file. FTP Server Reply: " + ftpClient.getReplyString());
            }
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }

    // Method to upload log file
    public static void uploadLogFile(String server, String user, String password, int port, String directoryPath, String logFileName, String logContent) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(directoryPath);

            // Convert logContent String to InputStream
            InputStream inputStream = new ByteArrayInputStream(logContent.getBytes(StandardCharsets.UTF_8));

            // Upload file
            boolean uploaded = ftpClient.storeFile(logFileName, inputStream);
            inputStream.close();

            if (!uploaded) {
                System.out.println("Failed to upload log file. FTP Server Reply: " + ftpClient.getReplyString());
            }else{
                System.out.println("success " + ftpClient.getReplyString());
            }
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }
}