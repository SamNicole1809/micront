package com.micront.printing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/p/r1")
    @PreAuthorize("hasAnyAuthority('p1')")
    public String r1() {
        return "OK";
    }

    @GetMapping(value = "/login")
    public String login(String username, String password) {
        String url = "http://localhost:9103/auth/oauth/token?grant_type=password&client_id=c1&client_secret=secret&username=" + username + "&password=" + password;
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        return response.getBody();
    }

}
