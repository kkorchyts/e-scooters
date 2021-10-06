package com.kkorchyts.dto.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kkorchyts.domain.enums.RentalStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RentalDto {
    private Integer id;
    private Integer userId;
    private Integer vehicleId;
    private RentalStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startRentalDateTime;
    private Integer startRentalOfficeId;
    private Integer tariffId;
    private Integer discountId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishRentalDateTime;
    private Integer finishRentalOfficeId;
    private BigDecimal discountAmount;
    private BigDecimal totalCost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartRentalDateTime() {
        return startRentalDateTime;
    }

    public void setStartRentalDateTime(LocalDateTime startRentalDateTime) {
        this.startRentalDateTime = startRentalDateTime;
    }

    public Integer getStartRentalOfficeId() {
        return startRentalOfficeId;
    }

    public void setStartRentalOfficeId(Integer startRentalOfficeId) {
        this.startRentalOfficeId = startRentalOfficeId;
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public void setTariffId(Integer tariffId) {
        this.tariffId = tariffId;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public LocalDateTime getFinishRentalDateTime() {
        return finishRentalDateTime;
    }

    public void setFinishRentalDateTime(LocalDateTime finishRentalDateTime) {
        this.finishRentalDateTime = finishRentalDateTime;
    }

    public Integer getFinishRentalOfficeId() {
        return finishRentalOfficeId;
    }

    public void setFinishRentalOfficeId(Integer finishRentalOfficeId) {
        this.finishRentalOfficeId = finishRentalOfficeId;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
