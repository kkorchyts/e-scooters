package com.kkorchyts.dao.searchcriteria;

import com.kkorchyts.domain.entities.Location_;
import com.kkorchyts.domain.entities.RentalOffice;
import com.kkorchyts.domain.entities.RentalOffice_;
import com.kkorchyts.domain.enums.address.Cities;
import com.kkorchyts.domain.enums.address.Districts;
import com.kkorchyts.domain.enums.address.Streets;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RentalOfficeSearchCriteria implements SearchCriteria<RentalOffice> {
    private final Cities city;
    private final Districts district;
    private final Streets street;

    public static class Builder {
        private Cities city;
        private Districts district;
        private Streets street;

        public RentalOfficeSearchCriteria.Builder city(Cities city) {
            this.city = city;
            return this;
        }

        public RentalOfficeSearchCriteria.Builder district(Districts district) {
            this.district = district;
            return this;
        }

        public RentalOfficeSearchCriteria.Builder street(Streets street) {
            this.street = street;
            return this;
        }

        public RentalOfficeSearchCriteria build() {
            return new RentalOfficeSearchCriteria(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private RentalOfficeSearchCriteria(Builder builder) {
        this.city = builder.city;
        this.district = builder.district;
        this.street = builder.street;
    }

    @Override
    public Predicate toPredicate(Root<RentalOffice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (city != null) {
            predicates.add(criteriaBuilder.equal(root.get(RentalOffice_.LOCATION).get(Location_.CITY), city));
        }

        if (district != null) {
            predicates.add(criteriaBuilder.equal(root.get(RentalOffice_.LOCATION).get(Location_.DISTRICT), district));
        }

        if (street != null) {
            predicates.add(criteriaBuilder.equal(root.get(RentalOffice_.LOCATION).get(Location_.STREET), street));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
