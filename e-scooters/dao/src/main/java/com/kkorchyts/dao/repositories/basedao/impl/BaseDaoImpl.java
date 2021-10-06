package com.kkorchyts.dao.repositories.basedao.impl;

import com.kkorchyts.dao.repositories.basedao.BaseDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY, rollbackFor = {ConstraintViolationException.class})
public abstract class BaseDaoImpl<T> implements BaseDao<T, Integer> {
    protected static final Logger logger = LoggerFactory.getLogger(
            BaseDaoImpl.class);

    private Class<T> type;

    protected SessionFactory sessionFactory;

    @Override
    public Integer add(T t) {
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.save(t);
    }

    @Override
    public void remove(T t) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(t);
    }

    @Override
    public void update(T src) {
        Session session = sessionFactory.getCurrentSession();
        session.update(src);
    }

    @Override
    public Page<T> getAll(Pageable pageable, SearchCriteria<T> searchCriteria) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        if (searchCriteria != null) {
            criteriaQuery.where(searchCriteria.toPredicate(root, criteriaQuery, criteriaBuilder));
        }
        List<T> result = session.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .list();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> tCount = countQuery.from(type);

        countQuery.select(criteriaBuilder.count(tCount));
        if (searchCriteria != null) {
            countQuery.where(searchCriteria.toPredicate(tCount, countQuery, criteriaBuilder));
        }

        Long count = session.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public T findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        T t = session.get(getType(), id);
        if (t == null) {
            throw new EntityNotFoundException("Entity (class name = " + type.getName() + "; id = " + id + ") not found!");
        } else {
            return t;
        }
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}

