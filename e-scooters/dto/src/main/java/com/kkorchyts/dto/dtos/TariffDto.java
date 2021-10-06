package com.kkorchyts.dto.dtos;

import com.kkorchyts.domain.enums.PerUnitTime;

import java.math.BigDecimal;

public class TariffDto {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal timelyPrice;
    private PerUnitTime perUnitTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTimelyPrice() {
        return timelyPrice;
    }

    public void setTimelyPrice(BigDecimal timelyPrice) {
        this.timelyPrice = timelyPrice;
    }

    public PerUnitTime getPerUnitTime() {
        return perUnitTime;
    }

    public void setPerUnitTime(PerUnitTime perUnitTime) {
        this.perUnitTime = perUnitTime;
    }
}
