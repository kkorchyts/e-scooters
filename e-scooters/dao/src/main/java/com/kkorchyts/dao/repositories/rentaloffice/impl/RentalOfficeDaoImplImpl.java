package com.kkorchyts.dao.repositories.rentaloffice.impl;

import com.kkorchyts.dao.repositories.basedao.impl.BaseDaoImpl;
import com.kkorchyts.dao.repositories.rentaloffice.RentalOfficeDao;
import com.kkorchyts.domain.entities.RentalOffice;
import com.kkorchyts.domain.entities.RentalOffice_;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

@Repository
public class RentalOfficeDaoImplImpl extends BaseDaoImpl<RentalOffice> implements RentalOfficeDao {
    public RentalOfficeDaoImplImpl() {
        setType(RentalOffice.class);
    }

    @Override
    public int deleteById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaDelete<RentalOffice> criteriaDelete = criteriaBuilder.createCriteriaDelete(RentalOffice.class);
        Root<RentalOffice> root = criteriaDelete.from(RentalOffice.class);
        criteriaDelete
                .where(criteriaBuilder.equal(root.get(RentalOffice_.ID), id));
        return session.createQuery(criteriaDelete).executeUpdate();
    }
}
