package com.example.firstspringproject.controllers;

import com.example.firstspringproject.models.AuthResponse;
import com.example.firstspringproject.models.RegisterDetails;
import com.example.firstspringproject.models.UserDetailsDto;
import com.example.firstspringproject.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService  authService;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserDetailsDto register){
        return authService.addNewEmployee(register);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> Login(@RequestBody RegisterDetails login){
        AuthResponse response = authService.authenticate(login);  // or employeeService if it’s there
        return ResponseEntity.ok(response);
    }
}
