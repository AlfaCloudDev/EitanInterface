package com.eitanmedical.app.bydimporter.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Service
public class ByDODataService {

    private final String authHeader = "Basic X29kYXRhOldlbGNvbWUxMjM="; 

    public String sendPostRequestToByD(String uri, String postBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);

        HttpEntity<String> request = new HttpEntity<>(postBody, headers);
        return restTemplate.postForObject(uri, request, String.class);
    }

    public String sendGetRequestToByD(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
    
        ResponseEntity<String> response = restTemplate.exchange(
            uri, 
            HttpMethod.GET, 
            new HttpEntity<>(headers), 
            String.class
        );
    
        return response.getBody();
    }
}