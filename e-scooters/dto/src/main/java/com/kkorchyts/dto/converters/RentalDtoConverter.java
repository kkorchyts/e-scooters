package com.kkorchyts.dto.converters;

import com.kkorchyts.dao.repositories.discount.DiscountDao;
import com.kkorchyts.dao.repositories.rentaloffice.RentalOfficeDao;
import com.kkorchyts.dao.repositories.tariff.TariffDao;
import com.kkorchyts.dao.repositories.user.UserDao;
import com.kkorchyts.domain.entities.Discount;
import com.kkorchyts.domain.entities.Rental;
import com.kkorchyts.domain.entities.RentalOffice;
import com.kkorchyts.domain.entities.Tariff;
import com.kkorchyts.domain.entities.User;
import com.kkorchyts.domain.entities.Vehicle;
import com.kkorchyts.dto.dtos.RentalDto;
import com.kkorchyts.dto.exceptions.DtoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalDtoConverter implements BaseConverter<Rental, RentalDto> {

    private SessionFactory sessionFactory;

    private UserDao userDao;

    private RentalOfficeDao rentalOfficeDao;

    private TariffDao tariffDao;

    private DiscountDao discountDao;

    @Override
    public Rental createEntityFromDto(RentalDto dto) {
        if (dto == null) {
            throw new DtoException("Rental dto is null!");
        }
        return updateEntity(new Rental(), dto);
    }

    @Override
    public RentalDto createDtoFromEntity(Rental entity) {
        if (entity == null) {
            throw new DtoException("Rental entity is null!");
        }

        RentalDto rentalDto = new RentalDto();
        DtoUtils.setIfNotNull(entity::getId, rentalDto::setId);
        if (entity.getUser() != null) {
            DtoUtils.setIfNotNull(entity.getUser()::getId, rentalDto::setUserId);
        }
        if (entity.getVehicle() != null) {
            DtoUtils.setIfNotNull(entity.getVehicle()::getId, rentalDto::setVehicleId);
        }
        DtoUtils.setIfNotNull(entity::getStatus, rentalDto::setStatus);
        DtoUtils.setIfNotNull(entity::getStartRentalDateTime, rentalDto::setStartRentalDateTime);
        if (entity.getStartRentalOffice() != null) {
            DtoUtils.setIfNotNull(entity.getStartRentalOffice()::getId, rentalDto::setStartRentalOfficeId);
        }

        if (entity.getTariff() != null) {
            DtoUtils.setIfNotNull(entity.getTariff()::getId, rentalDto::setTariffId);
        }

        if (entity.getDiscount() != null) {
            DtoUtils.setIfNotNull(entity.getDiscount()::getId, rentalDto::setDiscountId);
        }

        DtoUtils.setIfNotNull(entity::getFinishRentalDateTime, rentalDto::setFinishRentalDateTime);

        if (entity.getFinishRentalOffice() != null) {
            DtoUtils.setIfNotNull(entity.getFinishRentalOffice()::getId, rentalDto::setFinishRentalOfficeId);
        }

        DtoUtils.setIfNotNull(entity::getDiscountAmount, rentalDto::setDiscountAmount);
        DtoUtils.setIfNotNull(entity::getTotalCost, rentalDto::setTotalCost);
        return rentalDto;
    }

    @Override
    public Rental updateEntity(Rental entity, RentalDto dto) {
        Session session = sessionFactory.getCurrentSession();

        DtoUtils.setIfNotNull(dto::getId, entity::setId);

        if (dto.getUserId() != null && !entity.getUser().getId().equals(dto.getUserId())) {
            User user = entity.getUser();
            entity.setUser(userDao.findById(dto.getUserId()));
            session.detach(user);
        }

        if (dto.getVehicleId() != null && !entity.getVehicle().getId().equals(dto.getVehicleId())) {
            Vehicle vehicle = entity.getVehicle();
            entity.setUser(userDao.findById(dto.getVehicleId()));
            session.detach(vehicle);
        }

        DtoUtils.setIfNotNull(dto::getStatus, entity::setStatus);
        DtoUtils.setIfNotNull(dto::getStartRentalDateTime, entity::setStartRentalDateTime);

        if (dto.getStartRentalOfficeId() != null && !entity.getStartRentalOffice().getId().equals(dto.getStartRentalOfficeId())) {
            RentalOffice startRentalOffice = entity.getStartRentalOffice();
            entity.setStartRentalOffice(rentalOfficeDao.findById(dto.getStartRentalOfficeId()));
            session.detach(startRentalOffice);
        }

        if (dto.getTariffId() != null && !entity.getTariff().getId().equals(dto.getTariffId())) {
            Tariff tariff = entity.getTariff();
            entity.setTariff(tariffDao.findById(dto.getTariffId()));
            session.detach(tariff);
        }

        if (dto.getDiscountId() != null && !entity.getDiscount().getId().equals(dto.getDiscountId())) {
            Discount discount = entity.getDiscount();
            entity.setDiscount(discountDao.findById(dto.getDiscountId()));
            session.detach(discount);
        }

        DtoUtils.setIfNotNull(dto::getFinishRentalDateTime, entity::setFinishRentalDateTime);

        if (dto.getFinishRentalOfficeId() != null && !entity.getFinishRentalOffice().getId().equals(dto.getFinishRentalOfficeId())) {
            RentalOffice finishRentalOffice = entity.getFinishRentalOffice();
            entity.setFinishRentalOffice(rentalOfficeDao.findById(dto.getFinishRentalOfficeId()));
            session.detach(finishRentalOffice);
        }

        DtoUtils.setIfNotNull(dto::getDiscountAmount, entity::setDiscountAmount);
        DtoUtils.setIfNotNull(dto::getTotalCost, entity::setTotalCost);
        return entity;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
    public void setTariffDao(TariffDao tariffDao) {
        this.tariffDao = tariffDao;
    }

    @Autowired
    public void setDiscountDao(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }
}
