package com.kkorchyts.dao.repositories.tariff.impl;

import com.kkorchyts.dao.repositories.basedao.impl.BaseDaoImpl;
import com.kkorchyts.dao.repositories.tariff.TariffDao;
import com.kkorchyts.domain.entities.Tariff;
import org.springframework.stereotype.Repository;

@Repository
public class TariffDaoImplImpl extends BaseDaoImpl<Tariff> implements TariffDao {
    public TariffDaoImplImpl() {
        setType(Tariff.class);
    }
}
