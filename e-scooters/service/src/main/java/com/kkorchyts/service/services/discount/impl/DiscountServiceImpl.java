package com.kkorchyts.service.services.discount.impl;

import com.kkorchyts.dao.repositories.discount.DiscountDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Discount;
import com.kkorchyts.dto.converters.DiscountDtoConverter;
import com.kkorchyts.dto.dtos.DiscountDto;
import com.kkorchyts.service.services.discount.DiscountService;
import com.kkorchyts.service.validators.DiscountValidator;
import com.kkorchyts.startegies.discount.impl.RentalDiscount;
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
public class DiscountServiceImpl implements DiscountService {
    private static final Logger logger = LoggerFactory.getLogger(
            DiscountServiceImpl.class);

    private DiscountDao discountDao;
    private DiscountValidator discountValidator;
    private DiscountDtoConverter discountDtoConverter;

    @Override
    public BigDecimal calculateDiscountAmount(Discount discount, BigDecimal cost) {
        return RentalDiscount.create(discount).getDiscountAmount(cost);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public Page<DiscountDto> getAll(Pageable pageable, SearchCriteria<Discount> searchCriteria) {
        return discountDtoConverter.createFromEntitiesPage(discountDao.getAll(pageable, searchCriteria));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public DiscountDto create(DiscountDto discountDto) {
        //todo validator
        //discountValidator.validateNewData(vehicleDto);
        Discount discount = discountDtoConverter.createEntityFromDto(discountDto);
        discountDao.add(discount);
        return discountDtoConverter.createDtoFromEntity(discount);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void update(DiscountDto tariffDto) {
        //todo validator
        //discountValidator.validateDataForUpdate(vehicleDto);
        Discount discount = discountDao.findById(tariffDto.getId());
        discount = discountDtoConverter.updateEntity(discount, tariffDto);
        discountDao.update(discount);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public DiscountDto getById(Integer id) {
        //todo validator
        return discountDtoConverter.createDtoFromEntity(discountDao.findById(id));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void delete(Integer id) {
        Discount discount = discountDao.findById(id);
        discountDao.remove(discount);
    }

    @Autowired
    public void setDiscountDao(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    @Autowired
    public void setDiscountValidator(DiscountValidator discountValidator) {
        this.discountValidator = discountValidator;
    }

    @Autowired
    public void setDiscountDtoConverter(DiscountDtoConverter discountDtoConverter) {
        this.discountDtoConverter = discountDtoConverter;
    }
}
