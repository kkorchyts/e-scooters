package com.kkorchyts.dto.dtos;

import com.kkorchyts.domain.enums.UserRole;

public class RoleDto {
    private Integer id;
    private UserRole role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
