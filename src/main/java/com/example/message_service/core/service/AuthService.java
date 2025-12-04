package com.example.message_service.core.service;

import com.example.message_service.ui.DTO.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final String authServiceUrl;

    public AuthService(RestTemplate restTemplate,
                       @Value("http://auth-service:8081") String authServiceUrl) {
        this.restTemplate = restTemplate;
        this.authServiceUrl = authServiceUrl;
    }

    public UserDto validateBasicAuth(String authHeader) {
        try {
            return restTemplate.getForObject(
                    authServiceUrl + "/api/auth/validate?Authorization={authHeader}",
                    UserDto.class,
                    authHeader
            );
        } catch (HttpClientErrorException e) {
            System.out.println("Auth validation failed: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            System.out.println("Auth validation failed: " + e.getMessage());
            return null;
        }
    }

    public List<UserDto> getUsersByRole(String role) {
        try {
            UserDto[] users = restTemplate.getForObject(
                    authServiceUrl + "/api/auth/users/role/{role}",
                    UserDto[].class,
                    role
            );
            return users != null ? Arrays.asList(users) : List.of();
        } catch (HttpClientErrorException e) {
            System.out.println("Fetching users by role failed: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return List.of();
        } catch (Exception e) {
            System.out.println("Fetching users by role failed: " + e.getMessage());
            return List.of();
        }
    }
}
