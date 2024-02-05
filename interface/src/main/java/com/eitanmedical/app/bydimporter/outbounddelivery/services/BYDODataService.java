package com.eitanmedical.app.bydimporter.outbounddelivery.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import java.util.Base64;

@Service
public class BYDODataService {

    @Value("${EITAN_INTERFACE_OUTBOUND_ODATA_USER:_DATEX}")
    private String username; 

    @Value("${EITAN_INTERFACE_OUTBOUND_ODATA_PASSWORD:Welcome123}")
    private String password; 

    private String getAuthHeader() {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        return "Basic " + new String(encodedAuth);
    }

    public String sendPostRequestToByD(String uri, String postBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", getAuthHeader());
        
        HttpEntity<String> request = new HttpEntity<>(postBody, headers);
        
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // This catches client and server errors and returns the response body directly
            return "Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (RestClientException e) {
            // This catches other exceptions, like I/O errors
            return "Error: " + e.getMessage();
        }
    }

    public String sendGetRequestToByD(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getAuthHeader());
    
        try{
            ResponseEntity<String> response = restTemplate.exchange(
                uri, 
                HttpMethod.GET, 
                new HttpEntity<>(headers), 
                String.class
            );
        
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // This catches client and server errors and returns the response body directly
            return "Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (RestClientException e) {
            // This catches other exceptions, like I/O errors
            return "Error: " + e.getMessage();
        }
    }
}