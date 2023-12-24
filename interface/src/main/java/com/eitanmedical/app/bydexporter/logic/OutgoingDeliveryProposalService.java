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

import com.eitanmedical.app.bydexporter.boundries.OutgoingDeliveryProposal;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class OutgoingDeliveryProposalService implements OutgoingDeliveryProposalInterface{

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
    public ResponseEntity<Map<String, Object>> CreateAndUploadDeliveryProposal(OutgoingDeliveryProposal outgoingDeliveryProposal) {
        //Dotenv dotenv = Dotenv.configure().directory("/home/user/projects/interface/interface/").load();
		//String server = dotenv.get("SERVER");
        //String user = dotenv.get("USER");
        //String password = dotenv.get("PASSWORD");

        
        String returnValue = "";
		
        // Convert the object to JSON
        
        /*String appName = Helper.getAppName();
        String path = "";
        if (appName.equals("eitaninterface")){
            path = "drivehqshare/rgwoodfield/Eitan_SAP/Test/IN/DeliveryProposal/Input/";
        }
        else if (appName.equals("eitaninterfaceProd")) {
            path = "drivehqshare/rgwoodfield/Eitan_SAP/Prod/IN/DeliveryProposal/Input/";
        }*/
        String path = "drivehqshare/rgwoodfield/Eitan_SAP/Test/IN/DeliveryProposal/Input/";
        FTPClient ftpClient = new FTPClient();

	    try {
            String json = jackson.writeValueAsString(outgoingDeliveryProposal);
            InputStream inputStream = new ByteArrayInputStream(json.getBytes());
			ftpClient.connect(ftpServer, ftpPort);
			ftpClient.login(ftpUser, ftpPassword);

            ftpClient.enterLocalPassiveMode();
	
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			String remoteFile = path + "OutputDeliveryProposal" + outgoingDeliveryProposal.getReference() + ".json";
	
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
