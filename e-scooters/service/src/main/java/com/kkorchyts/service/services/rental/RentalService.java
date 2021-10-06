package com.kkorchyts.service.services.rental;

import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Rental;
import com.kkorchyts.dto.dtos.RentalDto;
import com.kkorchyts.dto.dtos.RentalFinishDto;
import com.kkorchyts.dto.dtos.RentalStartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentalService {
    Page<RentalDto> getAll(Pageable pageable, SearchCriteria<Rental> searchCriteria);

    RentalDto getById(Integer rentalId);

    RentalDto start(RentalStartDto rentalStartDto);

    void finish(Integer userId, RentalFinishDto rentalFinishDto);

    RentalDto getByLoginAndId(String login, Integer rentalId);
}
