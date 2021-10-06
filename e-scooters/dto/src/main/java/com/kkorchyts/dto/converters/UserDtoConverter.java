package com.kkorchyts.dto.converters;

import com.kkorchyts.dao.repositories.role.RoleDao;
import com.kkorchyts.domain.entities.Role;
import com.kkorchyts.domain.entities.User;
import com.kkorchyts.domain.enums.UserRole;
import com.kkorchyts.dto.dtos.UserDto;
import com.kkorchyts.dto.exceptions.DtoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserDtoConverter implements BaseConverter<User, UserDto> {

    private SessionFactory sessionFactory;

    private RoleDao roleDao;

    @Override
    public User createEntityFromDto(UserDto dto) {
        if (dto == null) {
            throw new DtoException("User dto is null!");
        }
        return updateEntity(new User(), dto);
    }

    @Override
    public UserDto createDtoFromEntity(User entity) {
        if (entity == null) {
            throw new DtoException("User entity is null!");
        }

        UserDto userDto = new UserDto();
        DtoUtils.setIfNotNull(entity::getId, userDto::setId);
        DtoUtils.setIfNotNull(entity::getLogin, userDto::setLogin);
        DtoUtils.setIfNotNull(entity::getPassword, userDto::setPassword);
        DtoUtils.setIfNotNull(entity::getName, userDto::setName);
        DtoUtils.setIfNotNull(entity::getEmail, userDto::setEmail);
        DtoUtils.setIfNotNull(entity::getAddress, userDto::setAddress);

        Set<UserRole> roles = new HashSet<>();
        entity.getRoles().forEach(role -> roles.add(role.getRole()));
        userDto.setRoles(roles);
        return userDto;
    }

    @Override
    public User updateEntity(User entity, UserDto dto) {
        Session session = sessionFactory.getCurrentSession();

        DtoUtils.setIfNotNull(dto::getId, entity::setId);
        DtoUtils.setIfNotNull(dto::getLogin, entity::setLogin);
        DtoUtils.setIfNotNull(dto::getPassword, entity::setPassword);
        DtoUtils.setIfNotNull(dto::getName, entity::setName);
        DtoUtils.setIfNotNull(dto::getEmail, entity::setEmail);
        DtoUtils.setIfNotNull(dto::getAddress, entity::setAddress);

        if (dto.getRoles() != null) {
            entity.getRoles().forEach(session::detach);
            entity.getRoles().clear();
            dto.getRoles().forEach(userRole -> {
                Role role = roleDao.findByRoleValue(userRole);
                entity.getRoles().add(role);
            });
        }

        return entity;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
