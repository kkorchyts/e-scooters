package com.kkorchyts.dto.dtos;

import com.kkorchyts.domain.enums.address.Cities;
import com.kkorchyts.domain.enums.address.Districts;
import com.kkorchyts.domain.enums.address.Streets;

public class LocationDto {
    private Integer id;
    private Cities city;
    private Districts district;
    private Streets street;

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

    public void setDistrict(Districts district) {
        this.district = district;
    }

    public Streets getStreet() {
        return street;
    }

    public void setStreet(Streets street) {
        this.street = street;
    }
}
