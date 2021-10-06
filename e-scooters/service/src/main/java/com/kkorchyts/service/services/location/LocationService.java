package com.kkorchyts.service.services.location;

import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Location;
import com.kkorchyts.dto.dtos.LocationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocationService {
    Page<LocationDto> getAll(Pageable pageable, SearchCriteria<Location> searchCriteria);

    void update(LocationDto locationDto);

    LocationDto create(LocationDto locationDto);

    LocationDto getById(Integer id);
}
