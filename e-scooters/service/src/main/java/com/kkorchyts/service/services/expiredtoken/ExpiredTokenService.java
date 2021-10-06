package com.kkorchyts.service.services.expiredtoken;

import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.ExpiredToken;
import com.kkorchyts.dto.dtos.ExpiredTokenDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpiredTokenService {
    Page<ExpiredTokenDto> getAll(Pageable pageable, SearchCriteria<ExpiredToken> searchCriteria);

    ExpiredTokenDto create(ExpiredTokenDto expiredTokenDto);

    void update(ExpiredTokenDto expiredTokenDto);

    ExpiredTokenDto getById(Integer id);

    void delete(Integer id);

    boolean isExpired(String token);
}
