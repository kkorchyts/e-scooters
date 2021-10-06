package com.kkorchyts.service.validators;

import com.kkorchyts.dao.repositories.location.LocationDao;
import com.kkorchyts.domain.entities.Location;
import com.kkorchyts.dto.dtos.LocationDto;
import com.kkorchyts.service.exception.IncorrectDataException;
import com.kkorchyts.service.utils.CommonUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class LocationValidator {
    private static final Logger logger = LoggerFactory.getLogger(
            LocationValidator.class);

    private SessionFactory sessionFactory;
    private LocationDao locationDao;

    public void validateNewLocationData(LocationDto locationDto) {
        if (locationDto.getCity() == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger, "City can't be null!");
        }
    }

    public void validateLocationDataForUpdate(LocationDto locationDto) {
        validateNewLocationData(locationDto);
        if (locationDto.getId() == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger, "Location id can't be null!");
        }
        Session session = sessionFactory.getCurrentSession();
        Location location = locationDao.findById(locationDto.getId());
        if (location == null) {
            CommonUtils.throwAndLogException(EntityNotFoundException.class, logger, "Location not found (Location id = " + locationDto.getId() + ")");
        }
        session.detach(location);
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }
}
