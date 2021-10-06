package com.kkorchyts.dao.repositories.user;

import com.kkorchyts.dao.repositories.basedao.BaseDao;
import com.kkorchyts.domain.entities.User;

import javax.persistence.EntityNotFoundException;

public interface UserDao extends BaseDao<User, Integer> {
    User findByLogin(String login) throws EntityNotFoundException;
    Boolean isExists(String login);
}
