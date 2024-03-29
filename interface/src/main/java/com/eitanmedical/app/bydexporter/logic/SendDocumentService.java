package com.eitanmedical.app.bydexporter.logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eitanmedical.app.bydexporter.boundries.GoodsReceiptProposal;
import com.eitanmedical.app.bydexporter.boundries.InboundDeliveryNote;
import com.eitanmedical.app.bydexporter.boundries.OutgoingDeliveryProposal;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SendDocumentService implements SendDocumentInterface{
    
    @Value("${EITAN_INTERFACE_FTP_PASSWORD:default_value}")
    private String ftpPassword;
    @Value("${EITAN_INTERFACE_FTP_USERNAME:default_value}")
    private String ftpUser;
    @Value("${EITAN_INTERFACE_FTP_SERVER:default_value}")
    private String ftpServer;
    @Value("${EITAN_INTERFACE_FTP_PORT:0}")
    private int ftpPort;
    
    private ObjectMapper jackson;

    @PostConstruct
    public void init(){
        this.jackson = new ObjectMapper();
    }
    

    @Override
    public ResponseEntity<Map<String, Object>> createAndUploadDeliveryProposal(
            OutgoingDeliveryProposal outgoingDeliveryProposal) {
        String path = "drivehqshare/rgwoodfield/Test/IN/DeliveryProposal/Input/";
        return this.sendDocument(outgoingDeliveryProposal, path);
    }

    @Override
    public ResponseEntity<Map<String, Object>> createAndUploadGoodsReceiptProposal(
            GoodsReceiptProposal goodsReceiptProposal) {
        String path = "drivehqshare/rgwoodfield/Test/IN/GoodsReceiptProposal/Input/";
        return this.sendDocument(goodsReceiptProposal, path);
    }


    @Override
    public ResponseEntity<Map<String, Object>> createAndUploadInboundDeliveryNote(
            InboundDeliveryNote inboundDeliveryNote) {
        String path = "drivehqshare/rgwoodfield/Test/IN/InboundDeliveryNote/Input/";
        return this.sendDocument(inboundDeliveryNote, path);
    }

    private ResponseEntity<Map<String, Object>> sendDocument (Object obj, String path){
        String returnValue = "";
        Map<String, Object> responseMap = new HashMap<>();
        FTPClient ftpClient = new FTPClient();

	    try {
            String json = "";
            String remoteFile = path;
            if (obj instanceof OutgoingDeliveryProposal){
                OutgoingDeliveryProposal outgoingDeliveryProposal = (OutgoingDeliveryProposal)obj;
                json = jackson.writeValueAsString(outgoingDeliveryProposal);
                remoteFile += "WarehouseRequest" + outgoingDeliveryProposal.getUniqueRequestID() + ".json";
            }
            else if (obj instanceof GoodsReceiptProposal){
                GoodsReceiptProposal goodsReceiptProposal = (GoodsReceiptProposal)obj;
                remoteFile += "GoodsReceiptProposal" + goodsReceiptProposal.getId() + ".json";
                json = jackson.writeValueAsString(goodsReceiptProposal);

            }
            else if (obj instanceof InboundDeliveryNote){
                InboundDeliveryNote inboundDeliveryNote = (InboundDeliveryNote)obj;
                json = jackson.writeValueAsString(inboundDeliveryNote);
                remoteFile += "InboundDeliveryNote" + inboundDeliveryNote.getWarehouseRequestID() + ".json";
            }
            InputStream inputStream = new ByteArrayInputStream(json.getBytes());
			ftpClient.connect(ftpServer, ftpPort);
			ftpClient.login(ftpUser, ftpPassword);

            ftpClient.enterLocalPassiveMode();
	
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			//String remoteFile = path + "InboundDeliveryNote" + inboundDeliveryNote.getId() + ".json";
			boolean success = ftpClient.storeFile(remoteFile, inputStream);
            if (success) {
                returnValue = "File " + remoteFile + " Uploaded successfully";
            } else {
                returnValue = "Failed to upload file to the FTP";
                responseMap.put("Message", returnValue);
                return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
            }
	
		} catch (IOException e) {
			e.printStackTrace();
            returnValue = "Failed to connect to FTP";
            responseMap.put("Message", returnValue);
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
		} finally {
			try {
				// Disconnect from the FTP server
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        responseMap.put("Message", returnValue);
        return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
    }
    
}
