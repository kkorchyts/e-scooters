package com.kkorchyts.dao.repositories.rentaloffice;

import com.kkorchyts.dao.repositories.basedao.BaseDao;
import com.kkorchyts.domain.entities.RentalOffice;

public interface RentalOfficeDao extends BaseDao<RentalOffice, Integer> {
    int deleteById(Integer id);
}
