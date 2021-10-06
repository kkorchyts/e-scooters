package com.kkorchyts.service.services.rentaloffice.impl;

import com.kkorchyts.dao.repositories.rentaloffice.RentalOfficeDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.dao.searchcriteria.VehicleSearchCriteria;
import com.kkorchyts.domain.entities.RentalOffice;
import com.kkorchyts.domain.entities.VehicleModelCount;
import com.kkorchyts.dto.converters.RentalOfficeDtoConverter;
import com.kkorchyts.dto.dtos.RentalOfficeDto;
import com.kkorchyts.dto.dtos.RentalOfficeExtraDetailDto;
import com.kkorchyts.dto.dtos.VehicleDto;
import com.kkorchyts.service.services.rentaloffice.RentalOfficeService;
import com.kkorchyts.service.services.vehicle.VehicleService;
import com.kkorchyts.service.validators.RentalOfficeValidator;
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
public class RentalOfficeServiceImpl implements RentalOfficeService {
    private static final Logger logger = LoggerFactory.getLogger(
            RentalOfficeServiceImpl.class);

    private RentalOfficeDao rentalOfficeDao;
    private RentalOfficeValidator rentalOfficeValidator;
    private RentalOfficeDtoConverter rentalOfficeDtoConverter;
    private VehicleService vehicleService;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public Page<RentalOfficeDto> getAll(Pageable pageable, SearchCriteria<RentalOffice> searchCriteria) {
        //todo validator
        return rentalOfficeDtoConverter.createFromEntitiesPage(rentalOfficeDao.getAll(pageable, searchCriteria));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void update(RentalOfficeDto rentalOfficeDto) {
        //todo validator
        rentalOfficeValidator.validateRentalOfficeDataForUpdate(rentalOfficeDto);
        RentalOffice rentalOffice = rentalOfficeDao.findById(rentalOfficeDto.getId());
        rentalOffice = rentalOfficeDtoConverter.updateEntity(rentalOffice, rentalOfficeDto);
        rentalOfficeDao.update(rentalOffice);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public RentalOfficeDto create(RentalOfficeDto rentalOfficeDto) {
        //todo validator
        rentalOfficeValidator.validateNewRentalOfficeData(rentalOfficeDto);
        RentalOffice rentalOffice = rentalOfficeDtoConverter.createEntityFromDto(rentalOfficeDto);
        rentalOfficeDao.add(rentalOffice);
        return rentalOfficeDtoConverter.createDtoFromEntity(rentalOffice);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public RentalOfficeDto getById(Integer id) {
        //todo validator
        return rentalOfficeDtoConverter.createDtoFromEntity(rentalOfficeDao.findById(id));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public Page<VehicleDto> getRentalOfficeVehicles(Integer rentalOfficeId, Boolean onlyAvailableVehicles, Pageable pageable) {
        //todo validator
        VehicleSearchCriteria vehicleSearchCriteria;
        if (onlyAvailableVehicles) {
            vehicleSearchCriteria = VehicleSearchCriteria.builder().rentalOffice(rentalOfficeId).available(onlyAvailableVehicles).build();
        } else {
            vehicleSearchCriteria = VehicleSearchCriteria.builder().rentalOffice(rentalOfficeId).build();
        }
        return vehicleService.getAllVehicles(pageable, vehicleSearchCriteria);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public RentalOfficeExtraDetailDto getRentalOfficeDetailByVehicleModels(Integer rentalOfficeId, Boolean onlyAvailableVehicles) {
        //todo validator
        VehicleSearchCriteria vehicleSearchCriteria;
        if (onlyAvailableVehicles) {
            vehicleSearchCriteria = VehicleSearchCriteria.builder().rentalOffice(rentalOfficeId).available(onlyAvailableVehicles).build();
        } else {
            vehicleSearchCriteria = VehicleSearchCriteria.builder().rentalOffice(rentalOfficeId).build();
        }
        List<VehicleModelCount> list = vehicleService.getVehiclesCountGroupedByModel(vehicleSearchCriteria, onlyAvailableVehicles);
        RentalOfficeDto rentalOfficeDto = getById(rentalOfficeId);
        return new RentalOfficeExtraDetailDto(rentalOfficeDto, list);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void moveVehiclesAndDeleteOffice(Integer id, Integer toId) {
        //todo validator
        moveVehiclesToRentalOffice(id, toId);
        rentalOfficeDao.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void moveVehiclesToRentalOffice(Integer fromId, Integer toId) {
        //todo validator
        vehicleService.moveVehiclesFromOfficeToOffice(fromId, toId);
    }

    @Autowired
    public void setRentalOfficeDao(RentalOfficeDao rentalOfficeDao) {
        this.rentalOfficeDao = rentalOfficeDao;
    }

    @Autowired
    public void setRentalOfficeValidator(RentalOfficeValidator rentalOfficeValidator) {
        this.rentalOfficeValidator = rentalOfficeValidator;
    }

    @Autowired
    public void setRentalOfficeDtoConverter(RentalOfficeDtoConverter rentalOfficeDtoConverter) {
        this.rentalOfficeDtoConverter = rentalOfficeDtoConverter;
    }

    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
}
