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
public class FTPReadService {

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

    public List<FileContent> readAllFiles(String server, String user, String password, int port, String directoryPath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        List<FileContent> fileContents = new ArrayList<>();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(directoryPath);

            FTPFile[] files = ftpClient.listFiles(directoryPath);
            for (FTPFile file : files) {
                if (!file.isFile()) {
                    continue;
                }

                String filePath = directoryPath + "/" + file.getName();
                InputStream inputStream = ftpClient.retrieveFileStream(filePath);
                String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                fileContents.add(new FileContent(content, filePath));
                ftpClient.completePendingCommand(); 
            }
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
        return fileContents;
    }

    public void moveToErrorDirectory(String server, String user, String password, int port, 
                                     String originalFilePath, String errorDirectoryPath) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();

            String errorFilePath = errorDirectoryPath + "/" + originalFilePath.substring(originalFilePath.lastIndexOf('/') + 1);
            boolean moved = ftpClient.rename(originalFilePath, errorFilePath);

            if (!moved) {
                throw new IOException("Failed to move file to error directory");
            }
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }
}