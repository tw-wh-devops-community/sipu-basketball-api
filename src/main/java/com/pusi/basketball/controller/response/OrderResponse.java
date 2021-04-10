package com.pusi.basketball.controller.response;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {
    private Long orderId;
    private String coupon;
    private BigDecimal originalAmount;
    private BigDecimal timeDiscount;
    private BigDecimal couponDiscount;
    private BigDecimal amount;

    private List<CourtBookingStatus> courts;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getTimeDiscount() {
        return timeDiscount;
    }

    public void setTimeDiscount(BigDecimal timeDiscount) {
        this.timeDiscount = timeDiscount;
    }

    public BigDecimal getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(BigDecimal couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<CourtBookingStatus> getCourts() {
        return courts;
    }

    public void setCourts(List<CourtBookingStatus> courts) {
        this.courts = courts;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
}
