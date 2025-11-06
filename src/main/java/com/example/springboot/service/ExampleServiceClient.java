package com.example.springboot.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExampleServiceClient {

    @Value("${example.service.url:http://localhost:8080}")
    private String serviceUrl;


    private final RestTemplate restTemplate;

    public ExampleServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Perform a GET and map response to the given type.
     */
    public <T> T get(Class<T> responseType) {
        ResponseEntity<T> response = restTemplate.getForEntity(serviceUrl, responseType);
        return response.getBody();
    }

}
