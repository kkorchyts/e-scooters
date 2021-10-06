package com.kkorchyts.service.services.vehiclemodel.impl;

import com.kkorchyts.dao.repositories.vehiclemodel.VehicleModelDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.VehicleModel;
import com.kkorchyts.dto.converters.VehicleModelDtoConverter;
import com.kkorchyts.dto.dtos.VehicleModelDto;
import com.kkorchyts.service.services.vehiclemodel.VehicleModelService;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleModelServiceImpl implements VehicleModelService {
    private static final Logger logger = LoggerFactory.getLogger(
            VehicleModelServiceImpl.class);

    private VehicleModelDao vehicleModelDao;
    private VehicleModelDtoConverter vehicleModelDtoConverter;

    @Autowired
    public VehicleModelServiceImpl(VehicleModelDao vehicleModelDao) {
        this.vehicleModelDao = vehicleModelDao;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public List<VehicleModelDto> getAllVehicleModels(Pageable pageable) {
        return vehicleModelDtoConverter.createFromEntities(vehicleModelDao.getAll(pageable, null).getContent());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public Page<VehicleModelDto> getAll(Pageable pageable, SearchCriteria<VehicleModel> searchCriteria) {
        return vehicleModelDtoConverter.createFromEntitiesPage(vehicleModelDao.getAll(pageable, searchCriteria));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public VehicleModelDto create(VehicleModelDto vehicleModelDto) {
        //todo validator
        //vehicleModelValidator.validateNewData(vehicleDto);
        VehicleModel vehicleModel = vehicleModelDtoConverter.createEntityFromDto(vehicleModelDto);
        vehicleModelDao.add(vehicleModel);
        return vehicleModelDtoConverter.createDtoFromEntity(vehicleModel);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void update(VehicleModelDto vehicleModelDto) {
        //todo validator
        //vehicleModelValidator.validateDataForUpdate(vehicleDto);
        VehicleModel vehicleModel = vehicleModelDao.findById(vehicleModelDto.getId());
        vehicleModel = vehicleModelDtoConverter.updateEntity(vehicleModel, vehicleModelDto);
        vehicleModelDao.update(vehicleModel);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public VehicleModelDto getById(Integer id) {
        //todo validator
        return vehicleModelDtoConverter.createDtoFromEntity(vehicleModelDao.findById(id));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void delete(Integer id) {
        VehicleModel vehicleModel = vehicleModelDao.findById(id);
        vehicleModelDao.remove(vehicleModel);
    }

    @Autowired
    public void setVehicleModelDtoConverter(VehicleModelDtoConverter vehicleModelDtoConverter) {
        this.vehicleModelDtoConverter = vehicleModelDtoConverter;
    }
}
