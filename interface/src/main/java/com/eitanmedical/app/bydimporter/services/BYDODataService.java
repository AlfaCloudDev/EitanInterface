package com.eitanmedical.app.bydimporter.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import java.util.Base64;

@Service
public class BYDODataService {

    @Value("${EITAN_INTERFACE_OUTBOUND_ODATA_USER:}")
    private String username; 

    @Value("${EITAN_INTERFACE_OUTBOUND_ODATA_PASSWORD:}")
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
        return restTemplate.postForObject(uri, request, String.class);
    }

    public String sendGetRequestToByD(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getAuthHeader());
    
        ResponseEntity<String> response = restTemplate.exchange(
            uri, 
            HttpMethod.GET, 
            new HttpEntity<>(headers), 
            String.class
        );
    
        return response.getBody();
    }
}