package com.kkorchyts.dao.repositories.rental;

import com.kkorchyts.dao.repositories.basedao.BaseDao;
import com.kkorchyts.domain.entities.Rental;

public interface RentalDao extends BaseDao<Rental, Integer> {
    Long getCountUnclosedRentalsByVehicle(Integer vehicleId);
}
