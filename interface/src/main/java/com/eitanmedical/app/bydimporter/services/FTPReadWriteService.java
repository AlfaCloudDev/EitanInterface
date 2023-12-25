package com.eitanmedical.app.bydimporter.services;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;
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
    
                // Move the file to the error directory
                if (!ftpClient.rename(originalFilePath, errorFilePath)) {
                    throw new IOException("Failed to move file: " + file.getName());
                }
    
                // Read the file from the error directory
                InputStream inputStream = ftpClient.retrieveFileStream(errorFilePath);
                String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                fileContents.add(new FileContent(content, errorFilePath));
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
                throw new IOException("Failed to delete file: " + filePath);
            }
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }
}