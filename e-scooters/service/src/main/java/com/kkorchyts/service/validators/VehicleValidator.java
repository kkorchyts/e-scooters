package com.kkorchyts.service.validators;

import com.kkorchyts.dao.repositories.rental.RentalDao;
import com.kkorchyts.dao.repositories.vehicle.VehicleDao;
import com.kkorchyts.domain.entities.Vehicle;
import com.kkorchyts.domain.enums.ChargeLevel;
import com.kkorchyts.dto.dtos.VehicleDto;
import com.kkorchyts.dto.exceptions.DtoException;
import com.kkorchyts.service.exception.VehicleIsUnavailable;
import com.kkorchyts.service.services.rental.impl.RentalServiceImpl;
import com.kkorchyts.service.utils.CommonUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleValidator {
    private static final Logger logger = LoggerFactory.getLogger(
            RentalServiceImpl.class);

    private SessionFactory sessionFactory;
    private VehicleDao vehicleDao;
    private RentalDao rentalDao;

    public void validateVehicleAvailability(Integer vehicleId) {
        Session session = sessionFactory.getCurrentSession();

        if (rentalDao.getCountUnclosedRentalsByVehicle(vehicleId) > 0) {

            CommonUtils.throwAndLogException(VehicleIsUnavailable.class, logger,
                    "Vehicle is unavailable to rent (id = " + vehicleId + ")");
        }

        Vehicle vehicle = vehicleDao.findById(vehicleId);

        if (ChargeLevel.getBadLevel().contains(vehicle.getChargeLevel())) {
            CommonUtils.throwAndLogException(VehicleIsUnavailable.class, logger,
                    "Vehicle is unavailable to rent (id = " + vehicleId + "; Charge level = \"" + vehicle.getChargeLevel() + "\")");
        }
        session.detach(vehicle);
    }

    public void validateNewVehicleData(VehicleDto vehicleDto) {
        if (vehicleDto == null) {
            throw new DtoException("Vehicle dto is null!");
        }
    }

    public void validateVehicleDataForUpdate(VehicleDto vehicleDto) {
        if (vehicleDto == null) {
            throw new DtoException("Vehicle dto is null!");
        }
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
