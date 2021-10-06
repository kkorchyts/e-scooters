package com.kkorchyts.dao.repositories.vehiclemodel.impl;

import com.kkorchyts.dao.repositories.basedao.impl.BaseDaoImpl;
import com.kkorchyts.dao.repositories.vehiclemodel.VehicleModelDao;
import com.kkorchyts.domain.entities.VehicleModel;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleModelDaoImplImpl extends BaseDaoImpl<VehicleModel> implements VehicleModelDao {
    public VehicleModelDaoImplImpl() {
        setType(VehicleModel.class);
    }

}
