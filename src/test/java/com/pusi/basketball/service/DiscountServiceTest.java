package com.pusi.basketball.service;

import com.pusi.basketball.controller.response.CourtBookingStatus;

import java.math.BigDecimal;

class DiscountServiceTest {

    CourtBookingStatus geneCourtStatus(String court, Integer subCourt, Integer amount, Integer periodHour) {
        CourtBookingStatus status = new CourtBookingStatus();
        status.setCourt(court);
        status.setSubCourt(subCourt);
        status.setPeriodHour(periodHour);
        status.setAmount(new BigDecimal(amount.toString()));
        return status;
    }

    // todo
}
