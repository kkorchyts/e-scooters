package com.kkorchyts.service.services.discount;

import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Discount;
import com.kkorchyts.dto.dtos.DiscountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface DiscountService {
    BigDecimal calculateDiscountAmount(Discount discount, BigDecimal cost);

    Page<DiscountDto> getAll(Pageable pageable, SearchCriteria<Discount> searchCriteria);

    DiscountDto create(DiscountDto discountDto);

    void update(DiscountDto discountDto);

    DiscountDto getById(Integer id);

    void delete(Integer id);
}
