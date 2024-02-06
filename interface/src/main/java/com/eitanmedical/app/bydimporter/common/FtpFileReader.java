package com.eitanmedical.app.bydimporter.common;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import java.util.ArrayList;
import java.util.List;

@Service
public class FtpFileReader {

    @Autowired
    private FtpConnectionManager connectionManager;

    // Inner class to hold both file content and file name
    public static class FileContentAndPath {
        private final String content;
        private final String fileName;

        public FileContentAndPath(String content, String fileName) {
            this.content = content;
            this.fileName = fileName;
        }

        public String getContent() {
            return content;
        }

        public String getFileName() {
            return fileName;
        }
    }

    public List<FileContentAndPath> readFiles(String directoryPath) throws IOException {
        List<FileContentAndPath> fileContentsAndPaths = new ArrayList<>();
        FTPClient ftpClient = connectionManager.openConnection();

        try {
            ftpClient.changeWorkingDirectory(directoryPath);
            FTPFile[] files = ftpClient.listFiles();

            for (FTPFile file : files) {
                if (file.isFile()) {
                    System.out.println("filename:" + file.getName());
                    try (InputStream inputStream = ftpClient.retrieveFileStream(file.getName())) {
                        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                        fileContentsAndPaths.add(new FileContentAndPath(content, file.getName()));
                        // Ensure the command is completed successfully
                        if (!ftpClient.completePendingCommand()) {
                            System.out.println("Could not complete the pending command. File: " + file.getName());
                        }
                    }
                }
            }
        } finally {
            connectionManager.closeConnection(ftpClient);
        }
        return fileContentsAndPaths;
    }
}