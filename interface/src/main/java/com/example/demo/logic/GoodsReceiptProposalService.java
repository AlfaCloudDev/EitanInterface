package com.example.demo.logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.boundries.GoodsReceiptProposal;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GoodsReceiptProposalService implements GoodsReceiptProposalInterface{

    private ObjectMapper jackson;

    @PostConstruct
    public void init(){
        this.jackson = new ObjectMapper();
    }

    @Override
    public ResponseEntity<Map<String, Object>> CreateAndUploadGoodsReceiptProposal(GoodsReceiptProposal goodsReceiptProposal) {
        String server = "ftp.drivehq.com";
        String user = "rhcleitan";
        String password = "rhcl1234";

        int port = 21;
        
        String returnValue = "";
		
        // Convert the object to JSON
        
        
        String path = "drivehqshare/rgwoodfield/Eitan_SAP/Test/IN/GoodsReceiptProposal/";

        FTPClient ftpClient = new FTPClient();

	    try {
            String json = jackson.writeValueAsString(goodsReceiptProposal);
            InputStream inputStream = new ByteArrayInputStream(json.getBytes());
			ftpClient.connect(server, port);
			ftpClient.login(user, password);

            ftpClient.enterLocalPassiveMode();
	
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			String remoteFile = path + "GoodsReceiptProposal" + goodsReceiptProposal.getId() + ".json";
	
			boolean success = ftpClient.storeFile(remoteFile, inputStream);
            if (success) {
                returnValue = "File " + remoteFile + " Uploaded successfully";
            } else {
                returnValue = "Failed to upload file";
            }
	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Disconnect from the FTP server
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("Message", returnValue);
        return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
    }
}
