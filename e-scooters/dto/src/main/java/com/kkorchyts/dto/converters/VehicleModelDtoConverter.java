package com.kkorchyts.dto.converters;

import com.kkorchyts.domain.entities.VehicleModel;
import com.kkorchyts.dto.dtos.VehicleModelDto;
import com.kkorchyts.dto.exceptions.DtoException;
import org.springframework.stereotype.Component;

@Component
public class VehicleModelDtoConverter implements BaseConverter<VehicleModel, VehicleModelDto> {

    @Override
    public VehicleModel createEntityFromDto(VehicleModelDto dto) {
        if (dto == null) {
            throw new DtoException("Vehicle Model dto is null!");
        }
        return updateEntity(new VehicleModel(), dto);
    }

    @Override
    public VehicleModelDto createDtoFromEntity(VehicleModel entity) {
        if (entity == null) {
            throw new DtoException("Vehicle Model entity is null!");
        }

        VehicleModelDto vehicleModelDto = new VehicleModelDto();
        DtoUtils.setIfNotNull(entity::getId, vehicleModelDto::setId);
        DtoUtils.setIfNotNull(entity::getName, vehicleModelDto::setName);
        return vehicleModelDto;
    }

    @Override
    public VehicleModel updateEntity(VehicleModel entity, VehicleModelDto dto) {
        DtoUtils.setIfNotNull(dto::getId, entity::setId);
        DtoUtils.setIfNotNull(dto::getName, entity::setName);
        return entity;
    }
}
