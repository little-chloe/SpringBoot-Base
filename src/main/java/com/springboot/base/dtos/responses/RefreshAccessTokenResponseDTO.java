package com.springboot.base.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RefreshAccessTokenResponseDTO {

    @JsonProperty("access_token")
    private String accessToken;
}
