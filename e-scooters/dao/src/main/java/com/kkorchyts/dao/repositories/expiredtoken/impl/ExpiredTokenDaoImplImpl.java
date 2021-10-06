package com.kkorchyts.dao.repositories.expiredtoken.impl;

import com.kkorchyts.dao.repositories.basedao.impl.BaseDaoImpl;
import com.kkorchyts.dao.repositories.expiredtoken.ExpiredTokenDao;
import com.kkorchyts.domain.entities.ExpiredToken;
import com.kkorchyts.domain.entities.ExpiredToken_;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class ExpiredTokenDaoImplImpl extends BaseDaoImpl<ExpiredToken> implements ExpiredTokenDao {
    public ExpiredTokenDaoImplImpl() {
        setType(ExpiredToken.class);
    }

    @Override
    public Optional<ExpiredToken> findByToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ExpiredToken> criteriaQuery = criteriaBuilder.createQuery(ExpiredToken.class);
        Root<ExpiredToken> root = criteriaQuery.from(ExpiredToken.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(ExpiredToken_.TOKEN), token));
        return session.createQuery(criteriaQuery).uniqueResultOptional();
    }
}
