package com.springboot.base.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.base.dtos.requests.CreateRoleRequestDTO;
import com.springboot.base.dtos.responses.CreateRoleResponseDTO;
import com.springboot.base.entities.RoleEntity;
import com.springboot.base.exceptions.DataExistedException;
import com.springboot.base.repositories.RoleRepository;
import com.springboot.base.services.RoleService;

import lombok.AllArgsConstructor;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public CreateRoleResponseDTO create(CreateRoleRequestDTO createRoleRequestDTO) {
        Optional<RoleEntity> roleEntity = roleRepository.findByName(createRoleRequestDTO.getName().toLowerCase());
        if (roleEntity.isPresent()) {
            throw new DataExistedException("Role %s existed".formatted(createRoleRequestDTO.getName()));
        }
        roleRepository.save(RoleEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name(createRoleRequestDTO.getName().toLowerCase())
                .build());
        return CreateRoleResponseDTO.builder()
                .message("Role created successfully")
                .build();
    }
}
