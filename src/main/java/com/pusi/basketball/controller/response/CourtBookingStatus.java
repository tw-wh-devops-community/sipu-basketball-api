package com.pusi.basketball.controller.response;

import java.math.BigDecimal;

public class CourtBookingStatus {
    private String court;
    private Integer subCourt;
    private Integer periodHour;
    private BigDecimal amount;

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public Integer getSubCourt() {
        return subCourt;
    }

    public void setSubCourt(Integer subCourt) {
        this.subCourt = subCourt;
    }

    public Integer getPeriodHour() {
        return periodHour;
    }

    public void setPeriodHour(Integer periodHour) {
        this.periodHour = periodHour;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
