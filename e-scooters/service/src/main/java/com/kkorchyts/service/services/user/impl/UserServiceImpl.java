package com.kkorchyts.service.services.user.impl;

import com.kkorchyts.dao.repositories.role.RoleDao;
import com.kkorchyts.dao.repositories.user.UserDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Role;
import com.kkorchyts.domain.entities.User;
import com.kkorchyts.domain.enums.UserRole;
import com.kkorchyts.dto.converters.UserDtoConverter;
import com.kkorchyts.dto.dtos.UserDto;
import com.kkorchyts.service.services.user.UserService;
import com.kkorchyts.service.validators.UserValidator;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(
            UserServiceImpl.class);

    private UserDao userDao;
    private RoleDao roleDao;

    private UserDtoConverter userDtoConverter;
    private UserValidator userValidator;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public Page<UserDto> getAll(Pageable pageable, SearchCriteria<User> searchCriteria) {
        return userDtoConverter.createFromEntitiesPage(userDao.getAll(pageable, searchCriteria));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void update(UserDto userDto) {
        userValidator.validateExistingUserDto(userDto);
        User user = userDao.findByLogin(userDto.getLogin());
        user = userDtoConverter.updateEntity(user, userDto);
        userDao.update(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = {EntityNotFoundException.class})
    @Override
    public UserDto create(UserDto userDto) {
        userValidator.validateNewUserDto(userDto);
        User user = userDtoConverter.createEntityFromDto(userDto);
        userDao.add(user);
        return userDtoConverter.createDtoFromEntity(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public UserDto findByLogin(String login) {
        return userDtoConverter.createDtoFromEntity(userDao.findByLogin(login));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void removeRole(UserDto userDto, UserRole userRole) {
        User user = userDao.findById(userDto.getId());
        Set<Role> roles = user.getRoles().stream()
                .filter(role -> !role.getRole().equals(userRole))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        userDao.update(user);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void grantRole(UserDto userDto, UserRole userRole) {
        User user = userDao.findById(userDto.getId());
        boolean userHasUserRole = user.getRoles().stream()
                .anyMatch(role -> role.getRole().equals(userRole));
        if (userHasUserRole) {
            return;
        }
        Role role = roleDao.findByRoleValue(userRole);
        user.getRoles().add(role);
        userDao.update(user);
    }

    @Autowired
    public void setUserDtoConverter(UserDtoConverter userDtoConverter) {
        this.userDtoConverter = userDtoConverter;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }
}