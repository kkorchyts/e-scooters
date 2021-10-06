package com.kkorchyts.dao.repositories.role.impl;

import com.kkorchyts.dao.repositories.basedao.impl.BaseDaoImpl;
import com.kkorchyts.dao.repositories.role.RoleDao;
import com.kkorchyts.domain.entities.Role;
import com.kkorchyts.domain.entities.Role_;
import com.kkorchyts.domain.enums.UserRole;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class RoleDaoImplImpl extends BaseDaoImpl<Role> implements RoleDao {
    public RoleDaoImplImpl() {
        setType(Role.class);
    }

    @Override
    public Role findByRoleValue(UserRole userRole) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> root = criteriaQuery.from(Role.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Role_.ROLE), userRole));
        return session.createQuery(criteriaQuery).uniqueResultOptional().orElseThrow(() -> new EntityNotFoundException("Role with value: " + userRole + " not found"));
    }
}
