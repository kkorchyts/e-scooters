package com.kkorchyts.dao.repositories.location.impl;

import com.kkorchyts.dao.repositories.basedao.impl.BaseDaoImpl;
import com.kkorchyts.dao.repositories.location.LocationDao;
import com.kkorchyts.domain.entities.Location;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDaoImplImpl extends BaseDaoImpl<Location> implements LocationDao {
    public LocationDaoImplImpl() {
        setType(Location.class);
    }
}
