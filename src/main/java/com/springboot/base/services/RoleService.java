package com.springboot.base.services;

import com.springboot.base.dtos.requests.CreateRoleRequestDTO;
import com.springboot.base.dtos.responses.CreateRoleResponseDTO;

public interface RoleService {

    CreateRoleResponseDTO create(CreateRoleRequestDTO createRoleRequestDTO);
}
