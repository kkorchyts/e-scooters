package com.kkorchyts.dto.converters;

import com.kkorchyts.domain.entities.Location;
import com.kkorchyts.dto.dtos.LocationDto;
import com.kkorchyts.dto.exceptions.DtoException;
import org.springframework.stereotype.Component;

@Component
public class LocationDtoConverter implements BaseConverter<Location, LocationDto> {
    @Override
    public Location createEntityFromDto(LocationDto dto) {
        if (dto == null) {
            throw new DtoException("Location dto is null!");
        }
        return updateEntity(new Location(), dto);

    }

    @Override
    public LocationDto createDtoFromEntity(Location entity) {
        if (entity == null) {
            throw new DtoException("Location entity is null!");
        }

        LocationDto locationDto = new LocationDto();
        DtoUtils.setIfNotNull(entity::getId, locationDto::setId);
        DtoUtils.setIfNotNull(entity::getCity, locationDto::setCity);
        DtoUtils.setIfNotNull(entity::getDistrict, locationDto::setDistrict);
        DtoUtils.setIfNotNull(entity::getStreet, locationDto::setStreet);
        return locationDto;
    }

    @Override
    public Location updateEntity(Location entity, LocationDto dto) {
        DtoUtils.setIfNotNull(dto::getId, entity::setId);
        DtoUtils.setIfNotNull(dto::getCity, entity::setCity);
        DtoUtils.setIfNotNull(dto::getDistrict, entity::setDistrict);
        DtoUtils.setIfNotNull(dto::getStreet, entity::setStreet);
        return entity;
    }
}
