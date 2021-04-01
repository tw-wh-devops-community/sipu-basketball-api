package com.pusi.basketball.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class CourtBooking {
    private Long id;
    private LocalDate date;
    private Integer hour;
    private String court;
    private Boolean isFullCourt;
    private Long orderId;

    @Id
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

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public Boolean getFullCourt() {
        return isFullCourt;
    }

    public void setFullCourt(Boolean fullCourt) {
        isFullCourt = fullCourt;
    }

    public Long getOrderId() {
        return orderId;
    }


    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


}
