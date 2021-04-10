package com.pusi.basketball.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private String coupon;
    private Boolean confirm = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }
}
