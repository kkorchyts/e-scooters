package com.kkorchyts.dao.repositories.rental.impl;

import com.kkorchyts.dao.repositories.basedao.impl.BaseDaoImpl;
import com.kkorchyts.dao.repositories.rental.RentalDao;
import com.kkorchyts.domain.entities.Rental;
import com.kkorchyts.domain.entities.Rental_;
import com.kkorchyts.domain.entities.Vehicle_;
import com.kkorchyts.domain.enums.RentalStatus;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class RentalDaoImplImpl extends BaseDaoImpl<Rental> implements RentalDao {
    public RentalDaoImplImpl() {
        setType(Rental.class);
    }

    public Long getCountUnclosedRentalsByVehicle(Integer vehicleId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Rental> tCount = countQuery.from(Rental.class);
        countQuery.select(criteriaBuilder.count(tCount)).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(tCount.get(Rental_.STATUS), RentalStatus.IN_PROGRESS),
                        criteriaBuilder.equal(tCount.get(Rental_.VEHICLE).get(Vehicle_.ID), vehicleId)
                ));
        return session.createQuery(countQuery).getSingleResult();
    }
}
