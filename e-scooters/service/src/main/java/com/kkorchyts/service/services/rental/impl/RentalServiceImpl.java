package com.kkorchyts.service.services.rental.impl;

import com.kkorchyts.dao.repositories.discount.DiscountDao;
import com.kkorchyts.dao.repositories.rental.RentalDao;
import com.kkorchyts.dao.repositories.rentaloffice.RentalOfficeDao;
import com.kkorchyts.dao.repositories.tariff.TariffDao;
import com.kkorchyts.dao.repositories.user.UserDao;
import com.kkorchyts.dao.repositories.vehicle.VehicleDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Rental;
import com.kkorchyts.domain.entities.RentalOffice;
import com.kkorchyts.domain.entities.Tariff;
import com.kkorchyts.domain.entities.User;
import com.kkorchyts.domain.entities.Vehicle;
import com.kkorchyts.domain.enums.RentalStatus;
import com.kkorchyts.dto.converters.DtoUtils;
import com.kkorchyts.dto.converters.RentalDtoConverter;
import com.kkorchyts.dto.dtos.RentalDto;
import com.kkorchyts.dto.dtos.RentalFinishDto;
import com.kkorchyts.dto.dtos.RentalStartDto;
import com.kkorchyts.dto.dtos.UserDto;
import com.kkorchyts.service.services.discount.DiscountService;
import com.kkorchyts.service.services.rental.RentalService;
import com.kkorchyts.service.services.tariff.TariffService;
import com.kkorchyts.service.services.vehicle.VehicleService;
import com.kkorchyts.service.utils.CommonUtils;
import com.kkorchyts.service.validators.RentalValidator;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
public class RentalServiceImpl implements RentalService {
    private static final Logger logger = LoggerFactory.getLogger(
            RentalServiceImpl.class);

    private VehicleDao vehicleDao;
    private RentalDao rentalDao;
    private TariffDao tariffDao;
    private UserDao userDao;
    private RentalOfficeDao rentalOfficeDao;
    private DiscountDao discountDao;

    private VehicleService vehicleService;
    private RentalDtoConverter rentalDtoConverter;
    private TariffService tariffService;
    private DiscountService discountService;

    private RentalValidator rentalValidator;

    public RentalServiceImpl() {
    }

    @Autowired
    public RentalServiceImpl(VehicleDao vehicleDao, RentalDao rentalDao, TariffDao tariffDao, UserDao userDao, RentalOfficeDao rentalOfficeDao, DiscountDao discountDao, VehicleService vehicleService, RentalDtoConverter rentalDtoConverter, TariffService tariffService, DiscountService discountService, RentalValidator rentalValidator) {
        this.vehicleDao = vehicleDao;
        this.rentalDao = rentalDao;
        this.tariffDao = tariffDao;
        this.userDao = userDao;
        this.rentalOfficeDao = rentalOfficeDao;
        this.discountDao = discountDao;
        this.vehicleService = vehicleService;
        this.rentalDtoConverter = rentalDtoConverter;
        this.tariffService = tariffService;
        this.discountService = discountService;
        this.rentalValidator = rentalValidator;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public Page<RentalDto> getAll(Pageable pageable, SearchCriteria<Rental> searchCriteria) {
        return rentalDtoConverter.createFromEntitiesPage(rentalDao.getAll(pageable, searchCriteria));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public RentalDto getById(Integer rentalId) {
        rentalValidator.validateRentalId(rentalId);
        return rentalDtoConverter.createDtoFromEntity(rentalDao.findById(rentalId));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public RentalDto start(RentalStartDto rentalStartDto) {
        rentalValidator.validateRentalStartDto(rentalStartDto);
        Vehicle vehicle = vehicleDao.findById(rentalStartDto.getVehicleId());
        User user = userDao.findById(rentalStartDto.getUserId());
        Tariff tariff = tariffDao.findById(rentalStartDto.getTariffId());
        Rental newRental = new Rental();
        newRental.setUser(user);
        newRental.setVehicle(vehicle);
        newRental.setTariff(tariff);
        newRental.setStatus(RentalStatus.IN_PROGRESS);
        newRental.setStartRentalOffice(vehicle.getCurrentRentalOffice());
        newRental.setStartRentalDateTime(rentalStartDto.getStartRentalDateTime());
        rentalDao.add(newRental);
        return rentalDtoConverter.createDtoFromEntity(newRental);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void finish(Integer userId, RentalFinishDto rentalFinishDto) {
        rentalValidator.validateRentalFinishDto(rentalFinishDto);
        Rental rental = rentalDao.findById(rentalFinishDto.getId());

        DtoUtils.setIfNotNull(rentalFinishDto.getVehicleTechnicalConditionDto()::getChargeLevel, rental.getVehicle()::setChargeLevel);
        DtoUtils.setIfNotNull(rentalFinishDto.getVehicleTechnicalConditionDto()::getTechnicalConditionLevel, rental.getVehicle()::setTechnicalConditionLevel);
        rental.setFinishRentalDateTime(rentalFinishDto.getFinishRentalDateTime());
        rental.setStatus(rentalFinishDto.getStatus());

        if (rentalFinishDto.getStatus().equals(RentalStatus.DONE)) {
            RentalOffice rentalOffice = rentalOfficeDao.findById(rentalFinishDto.getFinishRentalOfficeId());
            rental.getVehicle().setCurrentRentalOffice(rentalOffice);
            rental.setFinishRentalOffice(rentalOffice);
            if (rentalFinishDto.getDiscountId() != null) {
                rental.setDiscount(discountDao.findById(rentalFinishDto.getDiscountId()));
            }
            BigDecimal cost = tariffService.calculateCost(rental);
            BigDecimal discountAmount = discountService.calculateDiscountAmount(rental.getDiscount(), cost);
            rental.setDiscountAmount(discountAmount);
            rental.setTotalCost(cost.subtract(discountAmount));
        }
        rentalDao.update(rental);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public RentalDto getByLoginAndId(String login, Integer rentalId) throws RuntimeException{
        rentalValidator.validateRentalId(rentalId);
        Rental rental = rentalDao.findById(rentalId);
        if (!login.equalsIgnoreCase(rental.getUser().getLogin())) {
            CommonUtils.throwAndLogException(EntityNotFoundException.class, logger, "Rental not found (user login = " + login + "; id = " + rentalId + ")!)");
        }
        return rentalDtoConverter.createDtoFromEntity(rental);
    }


    @Autowired
    public void setVehicleDao(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Autowired
    public void setRentalDao(RentalDao rentalDao) {
        this.rentalDao = rentalDao;
    }

    @Autowired
    public void setTariffDao(TariffDao tariffDao) {
        this.tariffDao = tariffDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
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
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Autowired
    public void setRentalDtoConverter(RentalDtoConverter rentalDtoConverter) {
        this.rentalDtoConverter = rentalDtoConverter;
    }

    @Autowired
    public void setTariffService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @Autowired
    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Autowired
    public void setRentalValidator(RentalValidator rentalValidator) {
        this.rentalValidator = rentalValidator;
    }
}
