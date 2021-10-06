package com.kkorchyts.dto.dtos;

import com.kkorchyts.domain.enums.ChargeLevel;
import com.kkorchyts.domain.enums.TechnicalConditionLevel;

public class VehicleTechnicalConditionDto {
    private Integer id;
    private ChargeLevel chargeLevel;
    private TechnicalConditionLevel technicalConditionLevel;

    public VehicleTechnicalConditionDto() {
    }

    public VehicleTechnicalConditionDto(Integer id, ChargeLevel chargeLevel, TechnicalConditionLevel technicalConditionLevel) {
        this.id = id;
        this.chargeLevel = chargeLevel;
        this.technicalConditionLevel = technicalConditionLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
