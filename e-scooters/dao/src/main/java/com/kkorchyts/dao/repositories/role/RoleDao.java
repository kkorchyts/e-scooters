package com.kkorchyts.dao.repositories.role;

import com.kkorchyts.dao.repositories.basedao.BaseDao;
import com.kkorchyts.domain.entities.Role;
import com.kkorchyts.domain.enums.UserRole;


public interface RoleDao extends BaseDao<Role, Integer> {
    Role findByRoleValue(UserRole userRole);
}
