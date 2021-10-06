package com.kkorchyts.service.services.vehicle.impl;

import com.kkorchyts.dao.repositories.vehicle.VehicleDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Vehicle;
import com.kkorchyts.domain.entities.VehicleModelCount;
import com.kkorchyts.dto.converters.VehicleDtoConverter;
import com.kkorchyts.dto.dtos.VehicleDto;
import com.kkorchyts.service.services.vehicle.VehicleService;
import com.kkorchyts.service.validators.VehicleValidator;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private VehicleDao vehicleDao;
    private VehicleDtoConverter vehicleDtoConverter;
    private VehicleValidator vehicleValidator;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public Page<VehicleDto> getAllVehicles(Pageable pageable, SearchCriteria<Vehicle> searchCriteria) {
        return vehicleDtoConverter.createFromEntitiesPage(vehicleDao.getAll(pageable, searchCriteria));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public Page<VehicleDto> getAvailableVehicles(Pageable pageable, SearchCriteria<Vehicle> searchCriteria) {
        return vehicleDtoConverter.createFromEntitiesPage(vehicleDao.getAvailableVehicles(pageable, searchCriteria));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public List<VehicleModelCount> getVehiclesCountGroupedByModel(SearchCriteria<Vehicle> searchCriteria, Boolean onlyAvailableVehicles) {
        return vehicleDao.getVehiclesCountGroupedByModel(searchCriteria, onlyAvailableVehicles);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void moveVehiclesFromOfficeToOffice(Integer fromOffice, Integer toOffice) {
        vehicleDao.moveVehiclesFromOfficeToOffice(fromOffice, toOffice);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public VehicleDto create(VehicleDto vehicleDto) {
        //todo validator
        vehicleValidator.validateNewVehicleData(vehicleDto);
        Vehicle vehicle = vehicleDtoConverter.createEntityFromDto(vehicleDto);
        vehicleDao.add(vehicle);
        return vehicleDtoConverter.createDtoFromEntity(vehicle);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void update(VehicleDto vehicleDto) {
        //todo validator
        vehicleValidator.validateVehicleDataForUpdate(vehicleDto);
        Vehicle vehicle = vehicleDao.findById(vehicleDto.getId());
        vehicle = vehicleDtoConverter.updateEntity(vehicle, vehicleDto);
        vehicleDao.update(vehicle);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public VehicleDto getById(Integer id) {
        //todo validator
        return vehicleDtoConverter.createDtoFromEntity(vehicleDao.findById(id));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void delete(Integer id) {
        Vehicle vehicle = vehicleDao.findById(id);
        vehicleDao.remove(vehicle);
    }

    @Autowired
    public void setVehicleDao(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Autowired
    public void setVehicleDtoConverter(VehicleDtoConverter vehicleDtoConverter) {
        this.vehicleDtoConverter = vehicleDtoConverter;
    }

    @Autowired
    public void setVehicleValidator(VehicleValidator vehicleValidator) {
        this.vehicleValidator = vehicleValidator;
    }
}
