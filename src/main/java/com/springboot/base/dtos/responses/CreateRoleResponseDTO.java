package com.springboot.base.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateRoleResponseDTO {
    private String message;
}
