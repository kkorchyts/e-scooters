package com.kkorchyts.dto.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kkorchyts.domain.enums.ChargeLevel;
import com.kkorchyts.domain.enums.TechnicalConditionLevel;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class VehicleDto {
    private Integer id;
    private Integer vehicleModelId;
    private Integer currentRentalOfficeId;
    private String registrationNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;
    private ChargeLevel chargeLevel;
    private TechnicalConditionLevel technicalConditionLevel;
    private Set<Integer> rentalsIds;

    public VehicleDto(Integer id, Integer vehicleModelId, Integer currentRentalOfficeId, String registrationNumber, LocalDate registrationDate, ChargeLevel chargeLevel, TechnicalConditionLevel technicalConditionLevel, Set<Integer> rentalsIds) {
        this.id = id;
        this.vehicleModelId = vehicleModelId;
        this.currentRentalOfficeId = currentRentalOfficeId;
        this.registrationNumber = registrationNumber;
        this.registrationDate = registrationDate;
        this.chargeLevel = chargeLevel;
        this.technicalConditionLevel = technicalConditionLevel;
        this.rentalsIds = rentalsIds;
    }

    public VehicleDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVehicleModelId() {
        return vehicleModelId;
    }

    public void setVehicleModelId(Integer vehicleModelId) {
        this.vehicleModelId = vehicleModelId;
    }

    public Integer getCurrentRentalOfficeId() {
        return currentRentalOfficeId;
    }

    public void setCurrentRentalOfficeId(Integer currentRentalOfficeId) {
        this.currentRentalOfficeId = currentRentalOfficeId;
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

    public Set<Integer> getRentalsIds() {
        return rentalsIds;
    }

    public void setRentalsIds(Set<Integer> rentalsIds) {
        this.rentalsIds = rentalsIds;
    }
}
