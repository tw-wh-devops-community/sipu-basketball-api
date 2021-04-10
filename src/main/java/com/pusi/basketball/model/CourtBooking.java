package com.pusi.basketball.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "court_bookings")
public class CourtBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Integer hour;
    private Long orderId;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Court.class)
    @JoinColumn(referencedColumnName = "id")
    private Court court;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }
}
