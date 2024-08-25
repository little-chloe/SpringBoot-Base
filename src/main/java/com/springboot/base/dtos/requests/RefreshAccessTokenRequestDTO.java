package com.springboot.base.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RefreshAccessTokenRequestDTO {

    @JsonProperty("refresh_token")
    @NotEmpty(message = "Refresh token is required")
    private String refreshToken;
}
