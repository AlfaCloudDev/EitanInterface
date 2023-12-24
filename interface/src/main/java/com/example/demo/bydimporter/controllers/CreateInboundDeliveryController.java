package com.example.demo.bydimporter.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.commons.io.IOUtils;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class CreateInboundDeliveryController {

    private static final Logger logger = LoggerFactory.getLogger(CreateInboundDeliveryController.class);

    @GetMapping(path = "/CreateInboundDelivery", produces = MediaType.TEXT_PLAIN_VALUE)
    public String createInboundDelivery() {
        FTPClient ftpClient = new FTPClient();
        StringBuilder allResults = new StringBuilder();

        try {
            connectToFtpServer(ftpClient);
            FTPFile[] files = ftpClient.listFiles();

            for (FTPFile file : files) {
                if (file.isFile()) {
                    String fileContent = readFileContent(ftpClient, file);
                    String soapRequestBody = buildSOAPRequestBody(fileContent);
                    String result = sendSOAPRequest(soapRequestBody);
                    allResults.append(result).append("\n");
                }
            }
        } catch (IOException | UnirestException e) {
            logger.error("Error occurred: ", e);
            return "An error occurred: " + e.getMessage();
        } finally {
            disconnectFtpClient(ftpClient);
        }

        return allResults.toString();
    }

    private void connectToFtpServer(FTPClient ftpClient) throws IOException {
        String server = "ftp.drivehq.com";
        int port = 21;
        String user = "rhcleitan";
        String password = "rhcl1234";

        ftpClient.connect(server, port);
        if (!ftpClient.login(user, password)) {
            logger.error("FTP login failed for user: {}", user);
            throw new IOException("FTP login failed for user " + user);
        }
        ftpClient.enterLocalPassiveMode();
        ftpClient.changeWorkingDirectory("/drivehqshare/rgwoodfield/Eitan_SAP/Test/OUT/GoodsReceipt/Input");
    }

    // Method to read the content of a file from FTP server
    private String readFileContent(FTPClient ftpClient, FTPFile file) throws IOException {
        try (InputStream inputStream = ftpClient.retrieveFileStream(file.getName())) {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
    }

    private String buildSOAPRequestBody(String content) {
        // Parsing the file content as a JSON object
        JSONObject obj = new JSONObject(content);

        // Extracting relevant information from the JSON object
        String poID = obj.getString("ID");
        String supplierID = obj.getString("SupplierID");
        String siteID = obj.getString("SiteID");
        String deliveryDate = obj.getString("Purchase Order Delivery Date");

        // Building the SOAP request body
        String body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\">\r\n"
                + "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n"
                + "      <glob:StandardInboundDeliveryNotificationBundleCreateRequest_sync>\r\n"
                + "         <BasicMessageHeader />\r\n"
                + "         <StandardInboundDeliveryNotification actionCode=\"01\" cancellationDocumentIndicator=\"false\" releaseDocumentIndicator=\"true\">\r\n"
                + "         <DeliveryNotificationID>" + poID + "_in</DeliveryNotificationID>\r\n"
                + "         <ProcessingTypeCode>SD</ProcessingTypeCode>\r\n"
                + "         <DeliveryDate>\r\n"
                + "         <StartDateTime timeZoneCode=\"UTC\">" + deliveryDate + "T12:00:00.1234567Z</StartDateTime>\r\n"
                + "         <EndDateTime timeZoneCode=\"UTC\">" + deliveryDate + "T12:00:00.1234567Z</EndDateTime>\r\n"
                + "         </DeliveryDate>\r\n"
                + "         <ShipToLocationID>" + siteID + "</ShipToLocationID>\r\n"
                + "         <VendorID>" + siteID + "</VendorID>\r\n";

        // Processing each item in the JSON array
        JSONArray items = obj.getJSONArray("Item");
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String lineItemID = item.getString("LineItemID");
            String productID = item.getString("ProductID");
            String actualQuantity = item.getString("ReceivedPurchaseOrderQuantity");

            // Appending each item's details to the SOAP body
            body += "         <Item actionCode=\"01\" cancellationItemIndicator=\"false\">\r\n"
                    + "           <LineItemID>" + lineItemID + "</LineItemID>\r\n"
                    + "           <TypeCode>14</TypeCode>\r\n"
                    + "           <ProcessingTypeCode>INST</ProcessingTypeCode>\r\n"
                    + "           <DeliveryQuantity unitCode=\"EA\">" + actualQuantity + "</DeliveryQuantity>\r\n"
                    + "           <SellerPartyID>" + supplierID + "</SellerPartyID>\r\n"
                    + "           <BuyerPartyID>10000</BuyerPartyID>\r\n"
                    + "           <ItemProduct actionCode=\"01\">\r\n"
                    + "             <ProductID>" + productID + "</ProductID>\r\n";

            // Processing serial numbers for each item
            JSONArray serials = item.getJSONArray("SerialNumbers");
            for (int j = 0; j < serials.length(); j++) {
                JSONObject serial = serials.getJSONObject(j);
                String serialNum = serial.getString("SerialNumber");
                body += "             <SerialID>" + serialNum + "</SerialID>\r\n";
            }

            body += "           </ItemProduct>\r\n"
                    + "         </Item>\r\n";
        }

        body += "       </StandardInboundDeliveryNotification>\r\n"
                + "     </glob:StandardInboundDeliveryNotificationBundleCreateRequest_sync>\r\n"
                + "   </soapenv:Body>\r\n"
                + "</soapenv:Envelope>";

        return body;
    }

    private String sendSOAPRequest(String body) throws UnirestException {
        try {
            com.mashape.unirest.http.HttpResponse<String> response = Unirest
                    .post("https://my353793.sapbydesign.com/sap/bc/srt/scs/sap/managestandardinbounddeliveryn")
                    .header("Content-Type", "text/xml")
                    .header("Authorization", "Basic X0dPT0RTUFJPUE9TOldlbGNvbWUxMjM=")
                    .header("Cookie", "sap-usercontext=sap-client=559")
                    .body(body)
                    .asString();
            return response.getBody();
        } catch (UnirestException e) {
            logger.error("Error sending SOAP request", e);
            throw e;
        }
    }

    // Method to disconnect the FTP client
    private void disconnectFtpClient(FTPClient ftpClient) {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            logger.error("Error closing FTP connection", e);
        }
    }
}