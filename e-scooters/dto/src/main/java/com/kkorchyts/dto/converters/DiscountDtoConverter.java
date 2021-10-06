package com.kkorchyts.dto.converters;

import com.kkorchyts.domain.entities.Discount;
import com.kkorchyts.dto.dtos.DiscountDto;
import com.kkorchyts.dto.exceptions.DtoException;
import org.springframework.stereotype.Component;

@Component
public class DiscountDtoConverter implements BaseConverter<Discount, DiscountDto> {
    @Override
    public Discount createEntityFromDto(DiscountDto dto) {
        if (dto == null) {
            throw new DtoException("Discount Dto is null!");
        }
        return updateEntity(new Discount(), dto);
    }

    @Override
    public DiscountDto createDtoFromEntity(Discount entity) {
        if (entity == null) {
            throw new DtoException("Discount entity is null!");
        }

        DiscountDto discountDto = new DiscountDto();
        DtoUtils.setIfNotNull(entity::getId, discountDto::setId);
        DtoUtils.setIfNotNull(entity::getName, discountDto::setName);
        DtoUtils.setIfNotNull(entity::getDescription, discountDto::setDescription);
        DtoUtils.setIfNotNull(entity::getPercentage, discountDto::setDiscountPercentage);
        return discountDto;
    }

    @Override
    public Discount updateEntity(Discount entity, DiscountDto dto) {
        DtoUtils.setIfNotNull(dto::getId, entity::setId);
        DtoUtils.setIfNotNull(dto::getName, entity::setName);
        DtoUtils.setIfNotNull(dto::getDescription, entity::setDescription);
        DtoUtils.setIfNotNull(dto::getDiscountPercentage, entity::setPercentage);
        return entity;
    }
}
