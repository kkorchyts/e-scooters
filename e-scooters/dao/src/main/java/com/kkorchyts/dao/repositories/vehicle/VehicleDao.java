package com.kkorchyts.dao.repositories.vehicle;

import com.kkorchyts.dao.repositories.basedao.BaseDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Vehicle;
import com.kkorchyts.domain.entities.VehicleModelCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleDao extends BaseDao<Vehicle, Integer> {
    Page<Vehicle> getAvailableVehicles(Pageable pageable, SearchCriteria<Vehicle> searchCriteria);

    List<VehicleModelCount> getVehiclesCountGroupedByModel(SearchCriteria<Vehicle> searchCriteria, Boolean onlyAvailableVehicles);

    int moveVehiclesFromOfficeToOffice(Integer fromId, Integer toId);
}
