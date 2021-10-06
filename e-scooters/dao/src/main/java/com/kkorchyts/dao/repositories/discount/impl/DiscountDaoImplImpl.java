package com.kkorchyts.dao.repositories.discount.impl;

import com.kkorchyts.dao.repositories.basedao.impl.BaseDaoImpl;
import com.kkorchyts.dao.repositories.discount.DiscountDao;
import com.kkorchyts.domain.entities.Discount;
import org.springframework.stereotype.Repository;

@Repository
public class DiscountDaoImplImpl extends BaseDaoImpl<Discount> implements DiscountDao {
    public DiscountDaoImplImpl() {
        setType(Discount.class);
    }
}
