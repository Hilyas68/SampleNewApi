package com.example.NewsAPI.controller;

import com.example.NewsAPI.vo.request.LoginDto;
import com.example.NewsAPI.vo.request.SignUpDto;
import com.example.NewsAPI.vo.response.LoginResponse;
import com.example.NewsAPI.vo.response.ServiceResponse;
import com.example.NewsAPI.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto request){

        LoginResponse response = authService.authenticate(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<ServiceResponse> authenticate(@RequestBody SignUpDto request){

        ServiceResponse response = authService.registerUser(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/authors")
    public ResponseEntity<ServiceResponse> getAuthors(){

        ServiceResponse response = authService.getAllAuthors();

        return ResponseEntity.ok(response);
    }
}

