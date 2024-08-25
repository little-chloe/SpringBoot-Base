package com.springboot.base.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.base.dtos.requests.LoginRequestDTO;
import com.springboot.base.dtos.requests.RegisterRequestDTO;
import com.springboot.base.dtos.responses.LoginResponseDTO;
import com.springboot.base.dtos.responses.RegisterResponseDTO;
import com.springboot.base.services.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = userService.login(loginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(loginResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        RegisterResponseDTO registerResponseDTO = userService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerResponseDTO);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloworld() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Hello World");
    }
}
