package com.example.message_service.core.service;

import com.example.message_service.ui.DTO.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AuthClient {

    private final RestTemplate restTemplate;

    @Value("http://auth-servicea:8081") // Peka på din User Profile Service
    private String authServiceUrl;

    @Autowired
    public AuthClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // OBS: validateBasicAuth är BORTTAGEN.

    /**
     * Hämtar användare baserat på roll från Auth Service.
     * Vi skickar med token (Bearer) som vi fick från frontend för att bevisa att vi får göra detta.
     */
    public List<UserDto> getUsersByRole(String role, String authHeader) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader); // Token Relay

            HttpEntity<Void> request = new HttpEntity<>(headers);
            String url = authServiceUrl + "/api/auth/users/role/" + role;

            ResponseEntity<UserDto[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    UserDto[].class
            );

            return response.getBody() != null ? Arrays.asList(response.getBody()) : Collections.emptyList();

        } catch (Exception e) {
            System.out.println("Failed to fetch users from auth-service: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}