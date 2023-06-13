package com.FreelancersBackend.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testcontroller")
public class TestController {

    @GetMapping
    public ResponseEntity<String> hello(Authentication authentication){
        return ResponseEntity.ok(String.format(
                "hello world: %s", authentication.getName()));
    }
}
