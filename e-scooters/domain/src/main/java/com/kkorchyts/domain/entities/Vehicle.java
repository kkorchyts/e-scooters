package com.kkorchyts.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kkorchyts.domain.enums.ChargeLevel;
import com.kkorchyts.domain.enums.TechnicalConditionLevel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "vehicle_model_id")
    private VehicleModel vehicleModel;

    //    @Transient
    @ManyToOne
    @JoinColumn(name = "current_rental_office_id")
    private RentalOffice currentRentalOffice;

    @Column(name = "registration_number")
    private String registrationNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "charge_level")
    private ChargeLevel chargeLevel;

    @Column(name = "technical_condition_level")
    private TechnicalConditionLevel technicalConditionLevel;

    @OneToMany
    @JoinColumn(name = "vehicle_id")
    private Set<Rental> rentals;

    public Vehicle(Integer id, VehicleModel vehicleModel, RentalOffice currentRentalOffice, String registrationNumber, LocalDate registrationDate, ChargeLevel chargeLevel, TechnicalConditionLevel technicalConditionLevel, Set<Rental> rentals) {
        this.id = id;
        this.vehicleModel = vehicleModel;
        this.currentRentalOffice = currentRentalOffice;
        this.registrationNumber = registrationNumber;
        this.registrationDate = registrationDate;
        this.chargeLevel = chargeLevel;
        this.technicalConditionLevel = technicalConditionLevel;
        this.rentals = rentals;
    }

    public Vehicle() {
        rentals = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vehicleModel_id=" + vehicleModel.getId() +
                ", currentRentalOffice_id=" + currentRentalOffice.getId() +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", registrationDate=" + registrationDate +
                ", chargeLevel=" + chargeLevel +
                ", technicalConditionLevel=" + technicalConditionLevel +
                ", rental_ids=" + rentals.stream().map(Rental::getId).toString() +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VehicleModel getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(VehicleModel vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public RentalOffice getCurrentRentalOffice() {
        return currentRentalOffice;
    }

    public void setCurrentRentalOffice(RentalOffice rentalOffice) {
        this.currentRentalOffice = rentalOffice;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public ChargeLevel getChargeLevel() {
        return chargeLevel;
    }

    public void setChargeLevel(ChargeLevel chargeLevel) {
        this.chargeLevel = chargeLevel;
    }

    public TechnicalConditionLevel getTechnicalConditionLevel() {
        return technicalConditionLevel;
    }

    public void setTechnicalConditionLevel(TechnicalConditionLevel technicalConditionLevel) {
        this.technicalConditionLevel = technicalConditionLevel;
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(Set<Rental> rentals) {
        this.rentals = rentals;
    }
}