package com.example.demo.bydimporter.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class ByDODataService {

    public String sendPostRequestToByD(String uri, String postBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic X29kYXRhOldlbGNvbWUxMjM="); // Update this as needed

        HttpEntity<String> request = new HttpEntity<>(postBody, headers);
        return restTemplate.postForObject(uri, request, String.class);
    }
}