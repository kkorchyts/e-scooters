package com.kkorchyts.dao.repositories.expiredtoken;

import com.kkorchyts.dao.repositories.basedao.BaseDao;
import com.kkorchyts.domain.entities.ExpiredToken;

import java.util.Optional;

public interface ExpiredTokenDao extends BaseDao<ExpiredToken, Integer> {
    Optional<ExpiredToken> findByToken(String token);
}
