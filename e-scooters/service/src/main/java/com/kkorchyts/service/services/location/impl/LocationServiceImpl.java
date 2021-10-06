package com.kkorchyts.service.services.location.impl;

import com.kkorchyts.dao.repositories.location.LocationDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Location;
import com.kkorchyts.dto.converters.LocationDtoConverter;
import com.kkorchyts.dto.dtos.LocationDto;
import com.kkorchyts.service.services.location.LocationService;
import com.kkorchyts.service.validators.LocationValidator;
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
public class LocationServiceImpl implements LocationService {
    private static final Logger logger = LoggerFactory.getLogger(
            LocationServiceImpl.class);

    private LocationDao locationDao;
    private LocationValidator locationValidator;
    private LocationDtoConverter locationDtoConverter;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public Page<LocationDto> getAll(Pageable pageable, SearchCriteria<Location> searchCriteria) {
        return locationDtoConverter.createFromEntitiesPage(locationDao.getAll(pageable, searchCriteria));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void update(LocationDto locationDto) {
        locationValidator.validateLocationDataForUpdate(locationDto);
        Location location = locationDao.findById(locationDto.getId());
        location = locationDtoConverter.updateEntity(location, locationDto);
        locationDao.update(location);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public LocationDto create(LocationDto locationDto) {
        locationValidator.validateNewLocationData(locationDto);
        Location location = locationDtoConverter.createEntityFromDto(locationDto);
        locationDao.add(location);
        return locationDtoConverter.createDtoFromEntity(location);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public LocationDto getById(Integer id) {
        return locationDtoConverter.createDtoFromEntity(locationDao.findById(id));
    }

    @Autowired
    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Autowired
    public void setLocationValidator(LocationValidator locationValidator) {
        this.locationValidator = locationValidator;
    }

    @Autowired
    public void setLocationDtoConverter(LocationDtoConverter locationDtoConverter) {
        this.locationDtoConverter = locationDtoConverter;
    }
}
