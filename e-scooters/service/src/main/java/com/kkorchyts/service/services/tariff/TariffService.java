package com.kkorchyts.service.services.tariff;

import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Rental;
import com.kkorchyts.domain.entities.Tariff;
import com.kkorchyts.dto.dtos.TariffDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface TariffService {
    BigDecimal calculateCost(Rental rental);

    Page<TariffDto> getAll(Pageable pageable, SearchCriteria<Tariff> searchCriteria);

    TariffDto create(TariffDto tariffDto);

    void update(TariffDto tariffDto);

    TariffDto getById(Integer id);

    void delete(Integer id);
}
