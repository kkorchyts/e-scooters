package com.kkorchyts.domain.entities;

import com.kkorchyts.domain.enums.address.Cities;
import com.kkorchyts.domain.enums.address.Districts;
import com.kkorchyts.domain.enums.address.Streets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city")
    @Enumerated(EnumType.ORDINAL)
    private Cities city;

    @Column(name = "district")
    @Enumerated(EnumType.ORDINAL)
    private Districts district;

    @Column(name = "street")
    @Enumerated(EnumType.ORDINAL)
    private Streets street;

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", city=" + city +
                ", district=" + district +
                ", street=" + street +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public Districts getDistrict() {
        return district;
    }

    public void setDistrict(Districts distric) {
        this.district = distric;
    }

    public Streets getStreet() {
        return street;
    }

    public void setStreet(Streets street) {
        this.street = street;
    }
}