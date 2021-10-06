package com.kkorchyts.service.services.vehiclemodel;

import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.VehicleModel;
import com.kkorchyts.dto.dtos.VehicleModelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleModelService {
    List<VehicleModelDto> getAllVehicleModels(Pageable pageable);

    Page<VehicleModelDto> getAll(Pageable pageable, SearchCriteria<VehicleModel> searchCriteria);

    VehicleModelDto create(VehicleModelDto vehicleModelDto);

    void update(VehicleModelDto vehicleModelDto);

    VehicleModelDto getById(Integer id);

    void delete(Integer id);

}
