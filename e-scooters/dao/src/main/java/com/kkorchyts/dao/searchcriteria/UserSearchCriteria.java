package com.kkorchyts.dao.searchcriteria;

import com.kkorchyts.domain.entities.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserSearchCriteria implements SearchCriteria<User> {

    private UserSearchCriteria(Builder builder) {
    }

    public static class Builder {
        public UserSearchCriteria build() {
            return new UserSearchCriteria(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
