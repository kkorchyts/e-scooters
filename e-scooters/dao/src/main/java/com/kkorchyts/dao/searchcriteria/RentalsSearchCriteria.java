package com.kkorchyts.dao.searchcriteria;

import com.kkorchyts.domain.entities.Rental;
import com.kkorchyts.domain.entities.Rental_;
import com.kkorchyts.domain.entities.User_;
import com.kkorchyts.domain.entities.Vehicle_;
import com.kkorchyts.domain.enums.RentalStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentalsSearchCriteria implements SearchCriteria<Rental> {
    private final String login;
    private final Integer vehicleId;
    private final RentalStatus status;
    private final LocalDateTime dateTimeFrom;
    private final LocalDateTime dateTimeTo;

    public static class Builder {
        private String login;
        private Integer vehicleId;
        private RentalStatus status;
        private LocalDateTime dateTimeFrom;
        private LocalDateTime dateTimeTo;

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder vehicle(Integer vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public Builder status(RentalStatus status) {
            this.status = status;
            return this;
        }

        public Builder from(LocalDateTime dateTimeFrom) {
            this.dateTimeFrom = dateTimeFrom;
            return this;
        }

        public Builder to(LocalDateTime dateTimeTo) {
            this.dateTimeTo = dateTimeTo;
            return this;
        }

        public RentalsSearchCriteria build() {
            return new RentalsSearchCriteria(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private RentalsSearchCriteria(Builder builder) {
        this.login = builder.login;
        this.vehicleId = builder.vehicleId;
        this.status = builder.status;
        this.dateTimeFrom = builder.dateTimeFrom;
        this.dateTimeTo = builder.dateTimeTo;
    }

    @Override
    public Predicate toPredicate(Root<Rental> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (login != null && login.length() > 0) {
            predicates.add(criteriaBuilder.equal(root.get(Rental_.USER).get(User_.LOGIN), login));
        }

        if (vehicleId != null) {
            predicates.add(criteriaBuilder.equal(root.get(Rental_.VEHICLE).get(Vehicle_.ID), vehicleId));
        }

        if (status != null) {
            predicates.add(criteriaBuilder.equal(root.get(Rental_.STATUS), status));
        }

        if (dateTimeFrom != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Rental_.START_RENTAL_DATE_TIME), dateTimeFrom));
        }

        if (dateTimeFrom != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Rental_.FINISH_RENTAL_DATE_TIME), dateTimeTo));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
