package com.kkorchyts.domain.entities;

import com.kkorchyts.domain.enums.PerUnitTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "tariffs")
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "timely_price")
    private BigDecimal timelyPrice;

    @Column(name = "per_unit_time")
    @Enumerated(EnumType.ORDINAL)
    private PerUnitTime perUnitTime;

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", timelyPrice=" + timelyPrice +
                ", perUnitTime=" + perUnitTime +
                '}';
    }

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