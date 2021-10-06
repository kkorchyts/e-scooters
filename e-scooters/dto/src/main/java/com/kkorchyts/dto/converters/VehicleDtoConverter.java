package com.kkorchyts.dto.converters;

import com.kkorchyts.dao.repositories.rental.RentalDao;
import com.kkorchyts.dao.repositories.rentaloffice.RentalOfficeDao;
import com.kkorchyts.dao.repositories.vehiclemodel.VehicleModelDao;
import com.kkorchyts.domain.entities.Vehicle;
import com.kkorchyts.dto.dtos.VehicleDto;
import com.kkorchyts.dto.exceptions.DtoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class VehicleDtoConverter implements BaseConverter<Vehicle, VehicleDto> {

    private SessionFactory sessionFactory;

    private VehicleModelDao vehicleModelDao;

    private RentalDao rentalDao;

    private RentalOfficeDao rentalOfficeDao;

    @Override
    public Vehicle createEntityFromDto(VehicleDto dto) {
        return updateEntity(new Vehicle(), dto);
    }

    @Override
    public VehicleDto createDtoFromEntity(Vehicle entity) {
        if (entity == null) {
            throw new DtoException("Vehicle entity is null!");
        }

        VehicleDto vehicleDto = new VehicleDto();
        DtoUtils.setIfNotNull(entity::getId, vehicleDto::setId);
        if (entity.getVehicleModel() != null) {
            DtoUtils.setIfNotNull(entity.getVehicleModel()::getId, vehicleDto::setVehicleModelId);
        }

        if (entity.getCurrentRentalOffice() != null) {
            DtoUtils.setIfNotNull(entity.getCurrentRentalOffice()::getId, vehicleDto::setCurrentRentalOfficeId);
        }

        DtoUtils.setIfNotNull(entity::getRegistrationNumber, vehicleDto::setRegistrationNumber);
        DtoUtils.setIfNotNull(entity::getRegistrationDate, vehicleDto::setRegistrationDate);
        DtoUtils.setIfNotNull(entity::getChargeLevel, vehicleDto::setChargeLevel);
        DtoUtils.setIfNotNull(entity::getTechnicalConditionLevel, vehicleDto::setTechnicalConditionLevel);

        Set<Integer> rentalsIds = new HashSet<>();
        entity.getRentals().forEach(rental -> rentalsIds.add(rental.getId()));
        vehicleDto.setRentalsIds(rentalsIds);

        return vehicleDto;
    }

    @Override
    public Vehicle updateEntity(Vehicle entity, VehicleDto dto) {
        Session session = sessionFactory.getCurrentSession();

        if (dto.getVehicleModelId() != null &&
                (entity.getVehicleModel() == null || !entity.getVehicleModel().getId().equals(dto.getVehicleModelId()))) {
            //VehicleModel vehicleModel = entity.getVehicleModel();
            entity.setVehicleModel(vehicleModelDao.findById(dto.getVehicleModelId()));
            //session.detach(vehicleModel);
        }

        if (dto.getCurrentRentalOfficeId() != null &&
                (entity.getCurrentRentalOffice() == null || !entity.getCurrentRentalOffice().getId().equals(dto.getCurrentRentalOfficeId()))) {
//            RentalOffice currentRentalOffice = entity.getCurrentRentalOffice();
            entity.setCurrentRentalOffice(rentalOfficeDao.findById(dto.getCurrentRentalOfficeId()));
//            session.detach(currentRentalOffice);
        }

        DtoUtils.setIfNotNull(dto::getRegistrationNumber, entity::setRegistrationNumber);
        DtoUtils.setIfNotNull(dto::getRegistrationDate, entity::setRegistrationDate);
        DtoUtils.setIfNotNull(dto::getChargeLevel, entity::setChargeLevel);
        DtoUtils.setIfNotNull(dto::getTechnicalConditionLevel, entity::setTechnicalConditionLevel);

        if (dto.getRentalsIds() != null) {
            entity.getRentals().forEach(session::detach);
            entity.getRentals().clear();
            dto.getRentalsIds().forEach(rentalId -> {
                entity.getRentals().add(rentalDao.findById(rentalId));
            });
        }
        return entity;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setVehicleModelDao(VehicleModelDao vehicleModelDao) {
        this.vehicleModelDao = vehicleModelDao;
    }

    @Autowired
    public void setRentalDao(RentalDao rentalDao) {
        this.rentalDao = rentalDao;
    }

    @Autowired
    public void setRentalOfficeDao(RentalOfficeDao rentalOfficeDao) {
        this.rentalOfficeDao = rentalOfficeDao;
    }
}
