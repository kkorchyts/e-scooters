package com.kkorchyts.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kkorchyts.domain.enums.RentalStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "vehicle_id")
    @ManyToOne
    private Vehicle vehicle;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private RentalStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_rental_date_time")
    private LocalDateTime startRentalDateTime;

    @ManyToOne
    @JoinColumn(name = "start_rental_office_id")
    private RentalOffice startRentalOffice;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "finish_rental_date_time")
    private LocalDateTime finishRentalDateTime;

    @ManyToOne
    @JoinColumn(name = "finish_rental_office_id")
    private RentalOffice finishRentalOffice;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", user=" + user +
                ", vehicle_id=" + vehicle.getId() +
                ", status=" + status +
                ", startRentalDateTime=" + startRentalDateTime +
                ", startRentalOffice_id=" + startRentalOffice.getId() +
                ", tariff_id=" + tariff.getId() +
                ", discount_id=" + discount.getId() +
                ", finishRentalDateTime=" + finishRentalDateTime +
                ", finishRentalOffice_id=" + finishRentalOffice.getId() +
                ", discountAmount=" + discountAmount +
                ", totalCost=" + totalCost +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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

    public RentalOffice getStartRentalOffice() {
        return startRentalOffice;
    }

    public void setStartRentalOffice(RentalOffice startRentalOffice) {
        this.startRentalOffice = startRentalOffice;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public LocalDateTime getFinishRentalDateTime() {
        return finishRentalDateTime;
    }

    public void setFinishRentalDateTime(LocalDateTime finishRentalDateTime) {
        this.finishRentalDateTime = finishRentalDateTime;
    }

    public RentalOffice getFinishRentalOffice() {
        return finishRentalOffice;
    }

    public void setFinishRentalOffice(RentalOffice finishRentalOffice) {
        this.finishRentalOffice = finishRentalOffice;
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