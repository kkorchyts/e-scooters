package com.kkorchyts.dao.searchcriteria;

import com.kkorchyts.domain.entities.Rental;
import com.kkorchyts.domain.entities.RentalOffice_;
import com.kkorchyts.domain.entities.Rental_;
import com.kkorchyts.domain.entities.Vehicle;
import com.kkorchyts.domain.entities.Vehicle_;
import com.kkorchyts.domain.enums.ChargeLevel;
import com.kkorchyts.domain.enums.RentalStatus;
import com.kkorchyts.domain.enums.TechnicalConditionLevel;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

public class VehicleSearchCriteria implements SearchCriteria<Vehicle> {
    private final Integer modelId;
    private final Integer rentalOfficeId;
    private final ChargeLevel chargeLevel;
    private final TechnicalConditionLevel technicalConditionLevel;
    private final Boolean isAvailable;


    public static class Builder {
        private Integer modelId;
        private ChargeLevel chargeLevel;
        private Integer rentalOfficeId;
        private TechnicalConditionLevel technicalConditionLevel;
        private Boolean isAvailable;

        public Builder vehicleModel(Integer modelId) {
            this.modelId = modelId;
            return this;
        }

        public Builder rentalOffice(Integer rentalOfficeId) {
            this.rentalOfficeId = rentalOfficeId;
            return this;
        }

        public Builder chargeLevel(ChargeLevel chargeLevel) {
            this.chargeLevel = chargeLevel;
            return this;
        }

        public Builder technicalConditionLevel(TechnicalConditionLevel technicalConditionLevel) {
            this.technicalConditionLevel = technicalConditionLevel;
            return this;
        }

        public Builder available(Boolean isAvailable ) {
            this.isAvailable = isAvailable;
            return this;
        }

        public VehicleSearchCriteria build() {
            return new VehicleSearchCriteria(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private VehicleSearchCriteria(Builder builder) {
        this.modelId = builder.modelId;
        this.rentalOfficeId = builder.rentalOfficeId;
        this.chargeLevel = builder.chargeLevel;
        this.technicalConditionLevel = builder.technicalConditionLevel;
        this.isAvailable = builder.isAvailable;
    }

    @Override
    public Predicate toPredicate(Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (modelId != null) {
            predicates.add(criteriaBuilder.equal(root.get(Vehicle_.VEHICLE_MODEL).get(Vehicle_.ID), modelId));
        }

        if (rentalOfficeId != null) {
            predicates.add(criteriaBuilder.equal(root.get(Vehicle_.currentRentalOffice).get(RentalOffice_.ID), rentalOfficeId));
        }

        if (chargeLevel != null) {
            predicates.add(criteriaBuilder.equal(root.get(Vehicle_.CHARGE_LEVEL), chargeLevel));
        }

        if (technicalConditionLevel != null) {
            predicates.add(criteriaBuilder.equal(root.get(Vehicle_.TECHNICAL_CONDITION_LEVEL), technicalConditionLevel));
        }

        if (isAvailable != null) {
            if (isAvailable) {
                Subquery<Integer> vehicleSubQuery = query.subquery(Integer.class);
                Root<Rental> rootSubQuery = vehicleSubQuery.from(Rental.class);
                vehicleSubQuery.select(rootSubQuery.get(Rental_.VEHICLE).get(Vehicle_.ID)).where(criteriaBuilder.equal(rootSubQuery.get(Rental_.STATUS), RentalStatus.IN_PROGRESS));

                predicates.add(criteriaBuilder.not(criteriaBuilder.in(root.get(Vehicle_.ID)).value(vehicleSubQuery)));
                predicates.add(criteriaBuilder.in(root.get(Vehicle_.CHARGE_LEVEL)).value(ChargeLevel.getGoodLevel()));
                predicates.add(criteriaBuilder.in(root.get(Vehicle_.TECHNICAL_CONDITION_LEVEL)).value(TechnicalConditionLevel.getGoodLevel()));
            } else {
                Subquery<Integer> vehicleSubQuery = query.subquery(Integer.class);
                Root<Rental> rootSubQuery = vehicleSubQuery.from(Rental.class);
                vehicleSubQuery.select(rootSubQuery.get(Rental_.VEHICLE).get(Vehicle_.ID)).where(criteriaBuilder.notEqual(rootSubQuery.get(Rental_.STATUS), RentalStatus.IN_PROGRESS));

                predicates.add(criteriaBuilder.not(criteriaBuilder.in(root.get(Vehicle_.ID)).value(vehicleSubQuery)));
                predicates.add(criteriaBuilder.in(root.get(Vehicle_.CHARGE_LEVEL)).value(ChargeLevel.getBadLevel()));
                predicates.add(criteriaBuilder.in(root.get(Vehicle_.TECHNICAL_CONDITION_LEVEL)).value(TechnicalConditionLevel.getBadLevel()));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
