package com.kkorchyts.service.services.role.impl;

import com.kkorchyts.dao.repositories.role.RoleDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Role;
import com.kkorchyts.dto.converters.RoleDtoConverter;
import com.kkorchyts.dto.dtos.RoleDto;
import com.kkorchyts.service.services.role.RoleService;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(
            RoleServiceImpl.class);

    private RoleDao roleDao;

    private RoleDtoConverter roleDtoConverter;
    //private RoleValidator roleValidator;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public Page<RoleDto> getAll(Pageable pageable, SearchCriteria<Role> searchCriteria) {
        return roleDtoConverter.createFromEntitiesPage(roleDao.getAll(pageable, searchCriteria));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public RoleDto create(RoleDto roleDto) {
        //todo validator
        //RoleValidator.validateNewData(vehicleDto);
        Role role = roleDtoConverter.createEntityFromDto(roleDto);
        roleDao.add(role);
        return roleDtoConverter.createDtoFromEntity(role);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void update(RoleDto tariffDto) {
        //todo validator
        //RoleValidator.validateDataForUpdate(vehicleDto);
        Role role = roleDao.findById(tariffDto.getId());
        role = roleDtoConverter.updateEntity(role, tariffDto);
        roleDao.update(role);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public RoleDto getById(Integer id) {
        //todo validator
        return roleDtoConverter.createDtoFromEntity(roleDao.findById(id));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void delete(Integer id) {
        Role role = roleDao.findById(id);
        roleDao.remove(role);
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setRoleDtoConverter(RoleDtoConverter roleDtoConverter) {
        this.roleDtoConverter = roleDtoConverter;
    }
}
