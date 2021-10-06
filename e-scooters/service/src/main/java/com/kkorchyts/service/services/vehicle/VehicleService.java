package com.kkorchyts.service.services.vehicle;

import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Vehicle;
import com.kkorchyts.domain.entities.VehicleModelCount;
import com.kkorchyts.dto.dtos.VehicleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleService {
    VehicleDto create(VehicleDto vehicleDto);

    void update(VehicleDto vehicleDto);

    VehicleDto getById(Integer id);

    void delete(Integer id);

    Page<VehicleDto> getAllVehicles(Pageable pageable, SearchCriteria<Vehicle> searchCriteria);

    Page<VehicleDto> getAvailableVehicles(Pageable pageable, SearchCriteria<Vehicle> searchCriteria);

    List<VehicleModelCount> getVehiclesCountGroupedByModel(SearchCriteria<Vehicle> searchCriteria, Boolean onlyAvailableVehicles);

    void moveVehiclesFromOfficeToOffice(Integer fromOffice, Integer toOffice);
}
