package com.example.message_service.core.service;

import com.example.message_service.ui.DTO.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthClient {

    private final RestTemplate restTemplate;

    @Autowired
    public AuthClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto validateBasicAuth(String basicAuthHeader) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", basicAuthHeader);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            String url = "http://auth-service:8081/api/auth/validate";

            System.out.println("Calling auth-service with URL: " + url);
            System.out.println("Headers: " + headers);

            ResponseEntity<UserDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    UserDto.class
            );

            System.out.println("Response status: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody());

            return response.getBody();
        } catch (Exception e) {
            System.out.println("Auth validation failed: " + e.getMessage());
            return null;
        }
    }

}