package com.kkorchyts.service.services.expiredtoken.impl;

import com.kkorchyts.dao.repositories.expiredtoken.ExpiredTokenDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.ExpiredToken;
import com.kkorchyts.dto.converters.ExpiredTokenDtoConverter;
import com.kkorchyts.dto.dtos.ExpiredTokenDto;
import com.kkorchyts.service.services.expiredtoken.ExpiredTokenService;
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
public class ExpiredTokenServiceImpl implements ExpiredTokenService {
    private static final Logger logger = LoggerFactory.getLogger(
            ExpiredTokenServiceImpl.class);

    private ExpiredTokenDao expiredTokenDao;
    //    private ExpiredTokenValidator expiredTokenValidator;
    private ExpiredTokenDtoConverter expiredTokenDtoConverter;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public Page<ExpiredTokenDto> getAll(Pageable pageable, SearchCriteria<ExpiredToken> searchCriteria) {
        return expiredTokenDtoConverter.createFromEntitiesPage(expiredTokenDao.getAll(pageable, searchCriteria));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public ExpiredTokenDto create(ExpiredTokenDto expiredTokenDto) {
        //todo validator
        //expiredTokenValidator.validateNewData(vehicleDto);
        if (isExpired(expiredTokenDto.getToken())) {
            throw new RuntimeException("Token already exist");
        }
        ExpiredToken expiredToken = expiredTokenDtoConverter.createEntityFromDto(expiredTokenDto);
        expiredTokenDao.add(expiredToken);
        return expiredTokenDtoConverter.createDtoFromEntity(expiredToken);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void update(ExpiredTokenDto tariffDto) {
        //todo validator
        //expiredTokenValidator.validateDataForUpdate(vehicleDto);
        ExpiredToken expiredToken = expiredTokenDao.findById(tariffDto.getId());
        expiredToken = expiredTokenDtoConverter.updateEntity(expiredToken, tariffDto);
        expiredTokenDao.update(expiredToken);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public ExpiredTokenDto getById(Integer id) {
        //todo validator
        return expiredTokenDtoConverter.createDtoFromEntity(expiredTokenDao.findById(id));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public void delete(Integer id) {
        ExpiredToken expiredToken = expiredTokenDao.findById(id);
        expiredTokenDao.remove(expiredToken);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ConstraintViolationException.class})
    @Override
    public boolean isExpired(String token) {
        return expiredTokenDao.findByToken(token).isPresent();
    }

    @Autowired
    public void setExpiredTokenDao(ExpiredTokenDao expiredTokenDao) {
        this.expiredTokenDao = expiredTokenDao;
    }

    @Autowired
    public void setExpiredTokenDtoConverter(ExpiredTokenDtoConverter expiredTokenDtoConverter) {
        this.expiredTokenDtoConverter = expiredTokenDtoConverter;
    }
}
