package com.kkorchyts.dao.repositories.vehicle.impl;

import com.kkorchyts.dao.repositories.basedao.impl.BaseDaoImpl;
import com.kkorchyts.dao.repositories.vehicle.VehicleDao;
import com.kkorchyts.dao.searchcriteria.SearchCriteria;
import com.kkorchyts.domain.entities.Rental;
import com.kkorchyts.domain.entities.RentalOffice_;
import com.kkorchyts.domain.entities.Rental_;
import com.kkorchyts.domain.entities.Vehicle;
import com.kkorchyts.domain.entities.VehicleModelCount;
import com.kkorchyts.domain.entities.Vehicle_;
import com.kkorchyts.domain.enums.ChargeLevel;
import com.kkorchyts.domain.enums.RentalStatus;
import com.kkorchyts.domain.enums.TechnicalConditionLevel;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VehicleDaoImpl extends BaseDaoImpl<Vehicle> implements VehicleDao {
    public VehicleDaoImpl() {
        setType(Vehicle.class);
    }

    @Override
    public Page<Vehicle> getAvailableVehicles(Pageable pageable, SearchCriteria<Vehicle> searchCriteria) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Vehicle> criteriaQuery = criteriaBuilder.createQuery(Vehicle.class);
        Root<Vehicle> root = criteriaQuery.from(Vehicle.class);

        Subquery<Integer> vehicleSubQuery = criteriaQuery.subquery(Integer.class);
        Root<Rental> rootSubQuery = vehicleSubQuery.from(Rental.class);
        vehicleSubQuery.select(rootSubQuery.get(Rental_.VEHICLE).get(Vehicle_.ID)).where(criteriaBuilder.equal(rootSubQuery.get(Rental_.STATUS), RentalStatus.IN_PROGRESS));

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.not(criteriaBuilder.in(root.get(Vehicle_.ID)).value(vehicleSubQuery)));
        predicates.add(criteriaBuilder.in(root.get(Vehicle_.CHARGE_LEVEL)).value(ChargeLevel.getGoodLevel()));
        predicates.add(criteriaBuilder.in(root.get(Vehicle_.TECHNICAL_CONDITION_LEVEL)).value(TechnicalConditionLevel.getGoodLevel()));
        if (searchCriteria != null) {
            predicates.add(searchCriteria.toPredicate(root, criteriaQuery, criteriaBuilder));
        }

        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        List<Vehicle> vehicles = session.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .list();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Vehicle> tCount = countQuery.from(Vehicle.class);

        Subquery<Integer> vehicleCountSubquery = countQuery.subquery(Integer.class);
        Root<Rental> rootCountSubquery = vehicleCountSubquery.from(Rental.class);
        vehicleCountSubquery.select(rootCountSubquery.get(Rental_.VEHICLE).get(Vehicle_.ID)).where(criteriaBuilder.equal(rootCountSubquery.get(Rental_.STATUS), RentalStatus.IN_PROGRESS));

        predicates.clear();
        predicates.add(criteriaBuilder.not(criteriaBuilder.in(tCount.get(Vehicle_.ID)).value(vehicleCountSubquery)));
        predicates.add(criteriaBuilder.in(tCount.get(Vehicle_.CHARGE_LEVEL)).value(ChargeLevel.getGoodLevel()));
        predicates.add(criteriaBuilder.in(tCount.get(Vehicle_.TECHNICAL_CONDITION_LEVEL)).value(TechnicalConditionLevel.getGoodLevel()));
        if (searchCriteria != null) {
            predicates.add(searchCriteria.toPredicate(tCount, criteriaQuery, criteriaBuilder));
        }

        if (searchCriteria != null) {
            countQuery.where(searchCriteria.toPredicate(tCount, countQuery, criteriaBuilder));
        }

        countQuery.select(criteriaBuilder.count(tCount)).where(predicates.toArray(new Predicate[]{}));
        Long count = session.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(vehicles, pageable, count);
    }

    @Override
    public List<VehicleModelCount> getVehiclesCountGroupedByModel(SearchCriteria<Vehicle> searchCriteria, Boolean onlyAvailableVehicles) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<VehicleModelCount> criteriaQuery = criteriaBuilder.createQuery(VehicleModelCount.class);
        Root<Vehicle> root = criteriaQuery.from(Vehicle.class);

        List<Predicate> predicates = new ArrayList<>();
        if (onlyAvailableVehicles) {
            Subquery<Integer> vehicleSubquery = criteriaQuery.subquery(Integer.class);
            Root<Rental> rootSubquery = vehicleSubquery.from(Rental.class);
            vehicleSubquery.select(rootSubquery.get(Rental_.VEHICLE).get(Vehicle_.ID)).where(criteriaBuilder.equal(rootSubquery.get(Rental_.STATUS), RentalStatus.IN_PROGRESS));
            predicates.add(criteriaBuilder.not(criteriaBuilder.in(root.get(Vehicle_.ID)).value(vehicleSubquery)));
        }
        if (searchCriteria != null) {
            predicates.add(searchCriteria.toPredicate(root, criteriaQuery, criteriaBuilder));
        }

        criteriaQuery.multiselect(root.get(Vehicle_.VEHICLE_MODEL), criteriaBuilder.count(root.get(Vehicle_.VEHICLE_MODEL).get(Vehicle_.ID)))
                .where(predicates.toArray(new Predicate[]{}))
                .groupBy(root.get(Vehicle_.VEHICLE_MODEL));

        return session.createQuery(criteriaQuery)
                .list();
    }

    @Override
    public int moveVehiclesFromOfficeToOffice(Integer fromId, Integer toId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<Vehicle> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Vehicle.class);
        Root<Vehicle> root = criteriaUpdate.from(Vehicle.class);
        criteriaUpdate
                .set(root.get(Vehicle_.CURRENT_RENTAL_OFFICE).get(RentalOffice_.ID), toId)
                .where(criteriaBuilder.equal(root.get(Vehicle_.CURRENT_RENTAL_OFFICE).get(RentalOffice_.ID), fromId));
        return session.createQuery(criteriaUpdate).executeUpdate();
    }
}
