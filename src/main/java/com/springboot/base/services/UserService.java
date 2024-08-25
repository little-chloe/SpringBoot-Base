package com.springboot.base.services;

import com.springboot.base.dtos.requests.LoginRequestDTO;
import com.springboot.base.dtos.requests.RefreshAccessTokenRequestDTO;
import com.springboot.base.dtos.requests.RegisterRequestDTO;
import com.springboot.base.dtos.responses.LoginResponseDTO;
import com.springboot.base.dtos.responses.RefreshAccessTokenResponseDTO;
import com.springboot.base.dtos.responses.RegisterResponseDTO;

import jakarta.validation.Valid;

public interface UserService {

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);

    RefreshAccessTokenResponseDTO refreshAccessToken(@Valid RefreshAccessTokenRequestDTO refreshAccessTokenRequestDTO);
}
