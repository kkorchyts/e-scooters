package com.kkorchyts.dao.repositories.basedao;

import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseDao<T, PK> {
    PK add(T t);

    void remove(T t);

    void update(T src);

    Page<T> getAll(Pageable pageable, SearchCriteria<T> searchCriteria);

    T findById(Integer id);
}
