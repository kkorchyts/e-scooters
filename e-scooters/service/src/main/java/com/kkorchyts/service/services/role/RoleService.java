package com.kkorchyts.service.services.role;

import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Role;
import com.kkorchyts.dto.dtos.RoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Page<RoleDto> getAll(Pageable pageable, SearchCriteria<Role> searchCriteria);

    RoleDto create(RoleDto roleDto);

    void update(RoleDto roleDto);

    RoleDto getById(Integer id);

    void delete(Integer id);
}
