package com.kkorchyts.dto.converters;

import com.kkorchyts.domain.entities.Role;
import com.kkorchyts.dto.dtos.RoleDto;
import com.kkorchyts.dto.exceptions.DtoException;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoConverter implements BaseConverter<Role, RoleDto> {
    @Override
    public Role createEntityFromDto(RoleDto dto) {
        if (dto == null) {
            throw new DtoException("Role dto is null!");
        }
        return updateEntity(new Role(), dto);
    }

    @Override
    public RoleDto createDtoFromEntity(Role entity) {
        if (entity == null) {
            throw new DtoException("Role entity is null!");
        }
        RoleDto roleDto = new RoleDto();
        DtoUtils.setIfNotNull(entity::getId, roleDto::setId);
        DtoUtils.setIfNotNull(entity::getRole, roleDto::setRole);
        return roleDto;
    }

    @Override
    public Role updateEntity(Role entity, RoleDto dto) {
        DtoUtils.setIfNotNull(dto::getId, entity::setId);
        DtoUtils.setIfNotNull(dto::getRole, entity::setRole);
        return entity;
    }
}
