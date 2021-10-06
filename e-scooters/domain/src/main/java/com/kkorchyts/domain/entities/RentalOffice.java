package com.kkorchyts.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rental_offices")
public class RentalOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longtitude")
    private Float longtitude;

    @OneToMany
    @JoinColumn(name = "current_rental_office_id")
//   @JsonManagedReference
    private Set<Vehicle> vehicles;

    @OneToMany
    @JoinColumn(name = "start_rental_office_id")
//   @JsonManagedReference
    private Set<Rental> rentals;


    public RentalOffice() {
        vehicles = new HashSet<>();
        rentals = new HashSet<>();
    }

    public RentalOffice(Integer id, String name, Location location, Float latitude, Float longtitude, Set<Vehicle> vehicles, Set<Rental> rentals) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.vehicles = vehicles;
        this.rentals = rentals;
    }

    @Override
    public String toString() {
        return "RentalOffice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location_id=" + location.getId() +
                ", latitude=" + latitude +
                ", longtitude=" + longtitude +
                ", vehicle_ids=" + vehicles.stream().map(Vehicle::getId).toString() +
                ", rental_ids=" + rentals.stream().map(Rental::getId).toString() +
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(Set<Rental> rentals) {
        this.rentals = rentals;
    }
}