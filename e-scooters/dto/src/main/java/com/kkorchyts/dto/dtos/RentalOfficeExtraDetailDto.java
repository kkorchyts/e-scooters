package com.kkorchyts.dto.dtos;

import com.kkorchyts.domain.entities.VehicleModelCount;

import java.util.List;

public class RentalOfficeExtraDetailDto {
    private Integer id;
    private String name;
    private Integer locationId;
    private Float latitude;
    private Float longtitude;
    private List<VehicleModelCount> vehicleModelCountList;

    public RentalOfficeExtraDetailDto(RentalOfficeDto rentalOfficeDto, List<VehicleModelCount> vehicleModelCountList) {
        this.id = rentalOfficeDto.getId();
        this.name = rentalOfficeDto.getName();
        this.locationId = rentalOfficeDto.getId();
        this.latitude = rentalOfficeDto.getLatitude();
        this.longtitude = rentalOfficeDto.getLongtitude();
        this.vehicleModelCountList = vehicleModelCountList;
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

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Float longtitude) {
        this.longtitude = longtitude;
    }

    public List<VehicleModelCount> getVehicleModelCountList() {
        return vehicleModelCountList;
    }

    public void setVehicleModelCountList(List<VehicleModelCount> vehicleModelCountList) {
        this.vehicleModelCountList = vehicleModelCountList;
    }
}
