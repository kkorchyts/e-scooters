package com.kkorchyts.dto.converters;

import com.kkorchyts.dao.repositories.location.LocationDao;
import com.kkorchyts.dao.repositories.rental.RentalDao;
import com.kkorchyts.dao.repositories.vehicle.VehicleDao;
import com.kkorchyts.domain.entities.RentalOffice;
import com.kkorchyts.dto.dtos.RentalOfficeDto;
import com.kkorchyts.dto.exceptions.DtoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class RentalOfficeDtoConverter implements BaseConverter<RentalOffice, RentalOfficeDto> {

    private SessionFactory sessionFactory;

    private LocationDao locationDao;

    private VehicleDao vehicleDao;

    private RentalDao rentalDao;

    @Override
    public RentalOffice createEntityFromDto(RentalOfficeDto dto) {
        if (dto == null) {
            throw new DtoException("Rental Office dto is null!");
        }
        return updateEntity(new RentalOffice(), dto);
    }

    @Override
    public RentalOfficeDto createDtoFromEntity(RentalOffice entity) {
        if (entity == null) {
            throw new DtoException("Rental Office entity is null!");
        }

        RentalOfficeDto rentalOfficeDto = new RentalOfficeDto();
        DtoUtils.setIfNotNull(entity::getId, rentalOfficeDto::setId);
        if (entity.getLocation() != null) {
            DtoUtils.setIfNotNull(entity.getLocation()::getId, rentalOfficeDto::setLocationId);
        }
        DtoUtils.setIfNotNull(entity::getName, rentalOfficeDto::setName);
        DtoUtils.setIfNotNull(entity::getLatitude, rentalOfficeDto::setLatitude);
        DtoUtils.setIfNotNull(entity::getLongtitude, rentalOfficeDto::setLongtitude);

        HashSet<Integer> vehicleIds = new HashSet<>();
        entity.getVehicles().forEach(vehicle -> vehicleIds.add(vehicle.getId()));
        rentalOfficeDto.setVehicleIds(vehicleIds);

        HashSet<Integer> rentalsIds = new HashSet<>();
        entity.getRentals().forEach(rental -> rentalsIds.add(rental.getId()));
        rentalOfficeDto.setRentalsIds(rentalsIds);

        return rentalOfficeDto;
    }

    @Override
    public RentalOffice updateEntity(RentalOffice entity, RentalOfficeDto dto) {
        Session session = sessionFactory.getCurrentSession();

        DtoUtils.setIfNotNull(dto::getId, entity::setId);
        if (dto.getLocationId() != null
                && ((entity.getLocation() == null) || !entity.getLocation().getId().equals(dto.getLocationId()))) {
//            session.detach(entity.getLocation());
            entity.setLocation(locationDao.findById(dto.getLocationId()));
        }

        DtoUtils.setIfNotNull(dto::getName, entity::setName);
        DtoUtils.setIfNotNull(dto::getLatitude, entity::setLatitude);
        DtoUtils.setIfNotNull(dto::getLongtitude, entity::setLongtitude);

        if (entity.getVehicles() != null) {
            entity.getVehicles().forEach(session::detach);
            entity.getVehicles().clear();
            dto.getVehicleIds().forEach(vehicleId -> entity.getVehicles().add(vehicleDao.findById(vehicleId)));
        }

        if (dto.getRentalsIds() != null) {
            entity.getRentals().forEach(session::detach);
            entity.getRentals().clear();
            dto.getRentalsIds().forEach(rentalId -> entity.getRentals().add(rentalDao.findById(rentalId)));
        }

        return entity;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Autowired
    public void setVehicleDao(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Autowired
    public void setRentalDao(RentalDao rentalDao) {
        this.rentalDao = rentalDao;
    }
}
