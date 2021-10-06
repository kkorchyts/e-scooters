package com.kkorchyts.dto.converters;

import com.kkorchyts.domain.entities.Tariff;
import com.kkorchyts.dto.dtos.TariffDto;
import com.kkorchyts.dto.exceptions.DtoException;
import org.springframework.stereotype.Component;

@Component
public class TariffDtoConverter implements BaseConverter<Tariff, TariffDto> {
    @Override
    public Tariff createEntityFromDto(TariffDto dto) {
        if (dto == null) {
            throw new DtoException("Tariff dto is null!");
        }
        return updateEntity(new Tariff(), dto);
    }

    @Override
    public TariffDto createDtoFromEntity(Tariff entity) {
        if (entity == null) {
            throw new DtoException("Tariff entity is null!");
        }

        TariffDto tariffDto = new TariffDto();
        DtoUtils.setIfNotNull(entity::getId, tariffDto::setId);
        DtoUtils.setIfNotNull(entity::getDescription, tariffDto::setDescription);
        DtoUtils.setIfNotNull(entity::getTimelyPrice, tariffDto::setTimelyPrice);
        DtoUtils.setIfNotNull(entity::getPerUnitTime, tariffDto::setPerUnitTime);
        return tariffDto;
    }

    @Override
    public Tariff updateEntity(Tariff entity, TariffDto dto) {
        DtoUtils.setIfNotNull(dto::getId, entity::setId);
        DtoUtils.setIfNotNull(dto::getDescription, entity::setDescription);
        DtoUtils.setIfNotNull(dto::getTimelyPrice, entity::setTimelyPrice);
        DtoUtils.setIfNotNull(dto::getPerUnitTime, entity::setPerUnitTime);
        return entity;
    }
}