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
        
                // Use moveFile method to move the file
                boolean isMoved = moveFile(server, user, password, port, originalFilePath, errorFilePath);
                if (!isMoved) {
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

    public boolean moveFile(String server, String user, String password, int port, String sourceFilePath, String destinationFilePath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            
            // Attempt to move the file
            return ftpClient.rename(sourceFilePath, destinationFilePath);
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }

    // Method to upload log file
    public static void uploadLogFile(String server, String user, String password, int port, 
                                 String directoryPath, String logFileName, String logContent) {
    FTPClient ftpClient = new FTPClient();
    try {
        ftpClient.connect(server, port);
        ftpClient.login(user, password);
        ftpClient.enterLocalPassiveMode();
        ftpClient.changeWorkingDirectory(directoryPath);
        System.out.println("directoryPath: " + directoryPath);
        System.out.println("logFileName: " + logFileName);

        // Convert logContent String to InputStream
        InputStream inputStream = new ByteArrayInputStream(logContent.getBytes(StandardCharsets.UTF_8));

        // Upload file
        boolean uploaded = ftpClient.storeFile(logFileName, inputStream);
        inputStream.close();

        if (!uploaded) {
            System.out.println("Failed to upload log file. FTP Server Reply: " + ftpClient.getReplyString());
        } else {
            System.out.println("Success. FTP Server Reply: " + ftpClient.getReplyString());
        }
    } catch (IOException ex) {
        System.err.println("Error during FTP operation: " + ex.getMessage());
        // Optionally log the stack trace or additional details
    } finally {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException ex) {
                System.err.println("Error disconnecting FTP client: " + ex.getMessage());
            }
        }
    }
}
}