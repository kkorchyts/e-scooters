package com.kkorchyts.service.validators;

import com.kkorchyts.dao.repositories.discount.DiscountDao;
import com.kkorchyts.dao.repositories.rental.RentalDao;
import com.kkorchyts.dao.repositories.rentaloffice.RentalOfficeDao;
import com.kkorchyts.dao.repositories.user.UserDao;
import com.kkorchyts.domain.entities.Discount;
import com.kkorchyts.domain.entities.Rental;
import com.kkorchyts.domain.entities.RentalOffice;
import com.kkorchyts.domain.entities.User;
import com.kkorchyts.domain.enums.RentalStatus;
import com.kkorchyts.dto.dtos.RentalFinishDto;
import com.kkorchyts.dto.dtos.RentalStartDto;
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
public class RentalValidator {
    private static final Logger logger = LoggerFactory.getLogger(
            RentalValidator.class);

    private SessionFactory sessionFactory;
    private RentalDao rentalDao;
    private RentalOfficeDao rentalOfficeDao;
    private DiscountDao discountDao;
    private UserDao userDao;
    private VehicleValidator vehicleValidator;

    public void validateRentalFinishDto(RentalFinishDto rentalFinishDto) {
        // TODO think about using OrElseThrow

        Session session = sessionFactory.getCurrentSession();

        Rental rental = rentalDao.findById(rentalFinishDto.getId());
        if (rental == null) {
            CommonUtils.throwAndLogException(EntityNotFoundException.class, logger, "Rental not found (id = " + rentalFinishDto.getId() + ")");
        }

        if (!rental.getVehicle().getId().equals(rentalFinishDto.getVehicleTechnicalConditionDto().getId())) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                    "Incorrect input data, please, check! (rentalId = " + rentalFinishDto.getId() + "; vehicleId = " + rentalFinishDto.getVehicleTechnicalConditionDto().getId() + ")");
        }

        if (!rental.getStatus().equals(RentalStatus.IN_PROGRESS)) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                    "Impossible to stop closed rental (rentalId = " + rentalFinishDto.getId() + ")");
        }

        if (rentalFinishDto.getFinishRentalDateTime().isBefore(rental.getStartRentalDateTime())) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                    "Finish date should be after start date (rentalId = " + rentalFinishDto.getId()
                            + ", from: " + rental.getStartRentalDateTime()
                            + ", to : " + rentalFinishDto.getFinishRentalDateTime() + ")");
        }

        RentalOffice rentalOffice = rentalOfficeDao.findById(rentalFinishDto.getFinishRentalOfficeId());
        if (rentalOffice == null || rentalFinishDto.getFinishRentalDateTime() == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                    "Incorrect input Finish Rental data (finishRentalOfficeId = " + rentalFinishDto.getFinishRentalOfficeId() + "; finishRentalDateTime = " + rentalFinishDto.getFinishRentalDateTime() + ")");
        }

        if (rentalFinishDto.getDiscountId() != null) {
            Discount discount = discountDao.findById(rentalFinishDto.getDiscountId());
            if (discount == null) {
                CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                        "Incorrect input Discount (discountId = " + rentalFinishDto.getDiscountId() + ")");
            }
            session.detach(discount);
        }
        session.detach(rental);
        session.detach(rentalOffice);
    }

    public void validateRentalStartDto(RentalStartDto rentalStartDto) {
        Session session = sessionFactory.getCurrentSession();
        if (rentalStartDto.getUserId() == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                    "Incorrect input UserId (user id = null)");
        }

        User user = userDao.findById(rentalStartDto.getUserId());
        if (user == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                    "Incorrect input UserId (user id = " + rentalStartDto.getUserId() + ")");
        }
        session.detach(user);

        if (rentalStartDto.getStartRentalDateTime() == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger,
                    "Incorrect Start Rental Date (StartRentalDate = null)");
        }
        vehicleValidator.validateVehicleAvailability(rentalStartDto.getVehicleId());
    }

    public void validateRentalId(Integer id) {
        if (id == null) {
            CommonUtils.throwAndLogException(IncorrectDataException.class, logger, "Rental id is null!)");
        }

        Session session = sessionFactory.getCurrentSession();

        Rental rental = rentalDao.findById(id);
        if (rental == null) {
            CommonUtils.throwAndLogException(EntityNotFoundException.class, logger, "Rental not found (id = " + id + ")!)");
        }
        session.detach(rental);
    }

    @Autowired
    public void setVehicleValidator(VehicleValidator vehicleValidator) {
        this.vehicleValidator = vehicleValidator;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setRentalDao(RentalDao rentalDao) {
        this.rentalDao = rentalDao;
    }

    @Autowired
    public void setRentalOfficeDao(RentalOfficeDao rentalOfficeDao) {
        this.rentalOfficeDao = rentalOfficeDao;
    }

    @Autowired
    public void setDiscountDao(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
