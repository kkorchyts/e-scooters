package com.kkorchyts.dto.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kkorchyts.domain.enums.RentalStatus;

import java.time.LocalDateTime;

public class RentalFinishDto {
    private Integer id;
    private Integer userId;
    private VehicleTechnicalConditionDto vehicleTechnicalConditionDto;
    private RentalStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishRentalDateTime;
    private Integer finishRentalOfficeId;
    private Integer discountId;

    public RentalFinishDto() {
    }

    public RentalFinishDto(Integer id, Integer userId, VehicleTechnicalConditionDto vehicleTechnicalConditionDto, RentalStatus status, LocalDateTime finishRentalDateTime, Integer finishRentalOfficeId, Integer discountId) {
        this.id = id;
        this.userId = userId;
        this.vehicleTechnicalConditionDto = vehicleTechnicalConditionDto;
        this.status = status;
        this.finishRentalDateTime = finishRentalDateTime;
        this.finishRentalOfficeId = finishRentalOfficeId;
        this.discountId = discountId;
    }

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

    public VehicleTechnicalConditionDto getVehicleTechnicalConditionDto() {
        return vehicleTechnicalConditionDto;
    }

    public void setVehicleTechnicalConditionDto(VehicleTechnicalConditionDto vehicleTechnicalConditionDto) {
        this.vehicleTechnicalConditionDto = vehicleTechnicalConditionDto;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
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

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }
}
