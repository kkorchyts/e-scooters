package com.kkorchyts.service.services.rentaloffice;

import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.RentalOffice;
import com.kkorchyts.dto.dtos.RentalOfficeDto;
import com.kkorchyts.dto.dtos.RentalOfficeExtraDetailDto;
import com.kkorchyts.dto.dtos.VehicleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentalOfficeService {
    Page<RentalOfficeDto> getAll(Pageable pageable, SearchCriteria<RentalOffice> searchCriteria);

    void update(RentalOfficeDto rentalOfficeDto);

    RentalOfficeDto create(RentalOfficeDto rentalOfficeDto);

    RentalOfficeDto getById(Integer id);

    Page<VehicleDto> getRentalOfficeVehicles(Integer rentalOfficeId, Boolean onlyAvailableVehicles, Pageable pageable);

    RentalOfficeExtraDetailDto getRentalOfficeDetailByVehicleModels(Integer rentalOfficeId, Boolean onlyAvailableVehicles);

    void moveVehiclesAndDeleteOffice(Integer id, Integer toId);

    void moveVehiclesToRentalOffice(Integer fromId, Integer toId);
}
