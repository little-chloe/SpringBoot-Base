package com.springboot.base.services.impl;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.base.dtos.requests.LoginRequestDTO;
import com.springboot.base.dtos.requests.RegisterRequestDTO;
import com.springboot.base.dtos.responses.LoginResponseDTO;
import com.springboot.base.dtos.responses.RegisterResponseDTO;
import com.springboot.base.entities.UserEntity;
import com.springboot.base.exceptions.DataExistedException;
import com.springboot.base.repositories.RoleRepository;
import com.springboot.base.repositories.UserRepository;
import com.springboot.base.services.UserService;
import com.springboot.base.utils.JwtTokenGenerator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenGenerator jwtTokenGenerator;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return LoginResponseDTO.builder()
                    .accessToken(jwtTokenGenerator.generateAccessToken(authentication))
                    .refreshToken(jwtTokenGenerator.generateRefreshToken(authentication))
                    .build();
        }
        throw new BadCredentialsException("Invalid email or password");
    }

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(registerRequestDTO.getEmail());
        if (userEntity.isPresent()) {
            throw new DataExistedException(
                    "User with email %s is already exists".formatted(registerRequestDTO.getEmail()));
        }
        userRepository.save(UserEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .roles(Set.of(roleRepository.findByName("user").get()))
                .build());
        return RegisterResponseDTO.builder()
                .message("Registered successfully")
                .build();
    }

}
