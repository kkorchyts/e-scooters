package com.kkorchyts.dto.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class RentalStartDto {
    @JsonIgnore
    private Integer userId;

    private Integer vehicleId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startRentalDateTime;

    private Integer tariffId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDateTime getStartRentalDateTime() {
        return startRentalDateTime;
    }

    public void setStartRentalDateTime(LocalDateTime startRentalDateTime) {
        this.startRentalDateTime = startRentalDateTime;
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public void setTariffId(Integer tariffId) {
        this.tariffId = tariffId;
    }
}
