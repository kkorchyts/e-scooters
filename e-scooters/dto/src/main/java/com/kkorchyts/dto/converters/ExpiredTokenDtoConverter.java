package com.kkorchyts.dto.converters;

import com.kkorchyts.domain.entities.ExpiredToken;
import com.kkorchyts.dto.dtos.ExpiredTokenDto;
import com.kkorchyts.dto.exceptions.DtoException;
import org.springframework.stereotype.Component;

@Component
public class ExpiredTokenDtoConverter implements BaseConverter<ExpiredToken, ExpiredTokenDto> {
    @Override
    public ExpiredToken createEntityFromDto(ExpiredTokenDto dto) {
        if (dto == null) {
            throw new DtoException("Expired Token dto is null!");
        }
        return updateEntity(new ExpiredToken(), dto);
    }

    @Override
    public ExpiredTokenDto createDtoFromEntity(ExpiredToken entity) {
        if (entity == null) {
            throw new DtoException("Expired Token entity is null!");
        }

        ExpiredTokenDto expiredTokenDto = new ExpiredTokenDto();
        DtoUtils.setIfNotNull(entity::getId, expiredTokenDto::setId);
        DtoUtils.setIfNotNull(entity::getToken, expiredTokenDto::setToken);
        return expiredTokenDto;
    }

    @Override
    public ExpiredToken updateEntity(ExpiredToken entity, ExpiredTokenDto dto) {
        DtoUtils.setIfNotNull(dto::getId, entity::setId);
        DtoUtils.setIfNotNull(dto::getToken, entity::setToken);
        return entity;
    }
}
