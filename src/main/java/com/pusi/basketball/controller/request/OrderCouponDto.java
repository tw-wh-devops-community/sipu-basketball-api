package com.pusi.basketball.controller.request;

import javax.validation.constraints.NotNull;

public class OrderCouponDto {

    @NotNull
    private String coupon;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
}
