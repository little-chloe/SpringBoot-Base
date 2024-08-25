package com.springboot.base.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.base.dtos.requests.CreateRoleRequestDTO;
import com.springboot.base.dtos.responses.CreateRoleResponseDTO;
import com.springboot.base.services.RoleService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<CreateRoleResponseDTO> create(@Valid @RequestBody CreateRoleRequestDTO createRoleRequestDTO) {
        CreateRoleResponseDTO createRoleResponseDTO = roleService.create(createRoleRequestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(createRoleResponseDTO);
    }
}
