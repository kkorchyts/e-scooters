package com.kkorchyts.dao.repositories.user.impl;

import com.kkorchyts.dao.repositories.basedao.impl.BaseDaoImpl;
import com.kkorchyts.dao.repositories.user.UserDao;
import com.kkorchyts.domain.entities.User;
import com.kkorchyts.domain.entities.User_;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserDaoImplImpl extends BaseDaoImpl<User> implements UserDao {
    public UserDaoImplImpl() {
        setType(User.class);
    }

    @Override
    public User findByLogin(String login) throws EntityNotFoundException {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(User_.LOGIN), login));
        return session.createQuery(criteriaQuery).uniqueResultOptional().orElseThrow(() -> new EntityNotFoundException("Login with value: " + login + " not found"));
    }

    @Override
    public Boolean isExists(String login) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(User_.LOGIN), login));
        return session.createQuery(criteriaQuery).uniqueResultOptional().isPresent();
    }
}
