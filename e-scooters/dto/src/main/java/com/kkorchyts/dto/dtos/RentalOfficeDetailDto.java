package com.kkorchyts.dto.dtos;

import java.util.Set;

public class RentalOfficeDetailDto {
    private Integer id;
    private String name;
    private Integer locationId;
    private Float latitude;
    private Float longtitude;
    private Set<Integer> vehicleIds;
    private Set<Integer> rentalsIds;

    public RentalOfficeDetailDto() {
    }

    public RentalOfficeDetailDto(Integer id, String name, Integer locationId, Float latitude, Float longtitude, Set<Integer> vehicleIds, Set<Integer> rentalsIds) {
        this.id = id;
        this.name = name;
        this.locationId = locationId;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.vehicleIds = vehicleIds;
        this.rentalsIds = rentalsIds;
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

    public Set<Integer> getVehicleIds() {
        return vehicleIds;
    }

    public void setVehicleIds(Set<Integer> vehicleIds) {
        this.vehicleIds = vehicleIds;
    }

    public Set<Integer> getRentalsIds() {
        return rentalsIds;
    }

    public void setRentalsIds(Set<Integer> rentalsIds) {
        this.rentalsIds = rentalsIds;
    }
}

