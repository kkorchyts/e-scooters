package com.kkorchyts.service.services.tariff.impl;

import com.kkorchyts.dao.repositories.tariff.TariffDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Rental;
import com.kkorchyts.domain.entities.Tariff;
import com.kkorchyts.dto.converters.TariffDtoConverter;
import com.kkorchyts.dto.dtos.TariffDto;
import com.kkorchyts.service.services.tariff.TariffService;
import com.kkorchyts.service.validators.TariffValidator;
import com.kkorchyts.startegies.tariff.impl.TariffCreator;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TariffServiceImpl implements TariffService {
    private static final Logger logger = LoggerFactory.getLogger(
            TariffServiceImpl.class);

    private TariffDao tariffDao;

    private TariffDtoConverter tariffDtoConverter;
    private TariffValidator tariffValidator;

    @Override
    public BigDecimal calculateCost(Rental rental) {
        return TariffCreator.create(rental.getTariff()).getCost(rental.getStartRentalDateTime(), rental.getFinishRentalDateTime());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public Page<TariffDto> getAll(Pageable pageable, SearchCriteria<Tariff> searchCriteria) {
        return tariffDtoConverter.createFromEntitiesPage(tariffDao.getAll(pageable, searchCriteria));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public TariffDto create(TariffDto tariffDto) {
        //todo validator
        //TariffValidator.validateNewData(vehicleDto);
        Tariff tariff = tariffDtoConverter.createEntityFromDto(tariffDto);
        tariffDao.add(tariff);
        return tariffDtoConverter.createDtoFromEntity(tariff);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void update(TariffDto tariffDto) {
        //todo validator
        //TariffValidator.validateDataForUpdate(vehicleDto);
        Tariff tariff = tariffDao.findById(tariffDto.getId());
        tariff = tariffDtoConverter.updateEntity(tariff, tariffDto);
        tariffDao.update(tariff);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public TariffDto getById(Integer id) {
        //todo validator
        return tariffDtoConverter.createDtoFromEntity(tariffDao.findById(id));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void delete(Integer id) {
        Tariff tariff = tariffDao.findById(id);
        tariffDao.remove(tariff);
    }

    @Autowired
    public void setTariffDao(TariffDao tariffDao) {
        this.tariffDao = tariffDao;
    }

    @Autowired
    public void setTariffDtoConverter(TariffDtoConverter tariffDtoConverter) {
        this.tariffDtoConverter = tariffDtoConverter;
    }

    @Autowired
    public void setTariffValidator(TariffValidator tariffValidator) {
        this.tariffValidator = tariffValidator;
    }
}
