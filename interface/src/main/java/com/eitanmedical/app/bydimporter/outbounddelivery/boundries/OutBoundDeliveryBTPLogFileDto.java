package com.eitanmedical.app.bydimporter.outbounddelivery.boundries;

import java.util.Date;
import java.util.List;

public class OutBoundDeliveryBTPLogFileDto {
    private LogHeaderDto header;
    private List<FileLogEntryDto> fileLogEntries;

    public LogHeaderDto getHeader() {
        return header;
    }

    public void setHeader(LogHeaderDto header) {
        this.header = header;
    }

    public List<FileLogEntryDto> getFileLogEntries() {
        return fileLogEntries;
    }

    public void setFileLogEntries(List<FileLogEntryDto> fileLogEntries) {
        this.fileLogEntries = fileLogEntries;
    }

    public static class LogHeaderDto {
        private Date timestamp;

        public LogHeaderDto(Date date) {
            this.timestamp = date; // Set the timestamp
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        
    }

    public static class FileLogEntryDto {
        private String fileName;
        private List<String> errorMessages;

        public String getFileName() {
            return fileName;
        }
        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
        public List<String> getErrorMessages() {
            return errorMessages;
        }
        public void setErrorMessages(List<String> errorMessages) {
            this.errorMessages = errorMessages;
        }

        
    }
}