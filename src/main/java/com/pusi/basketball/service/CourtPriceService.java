package com.pusi.basketball.service;

import com.pusi.basketball.controller.response.CourtBookingStatus;
import com.pusi.basketball.controller.response.OrderResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.pusi.basketball.type.BookingHour.HOUR_9;
import static com.pusi.basketball.type.BookingHour.HOUR_18;
import static com.pusi.basketball.type.BookingHour.HOUR_20;
import static com.pusi.basketball.type.BookingHour.HOUR_22;

@Service
public class CourtPriceService {

    public static final BigDecimal HOUR_9_18_HALF_PRICE = BigDecimal.valueOf(50);
    public static final BigDecimal HOUR_9_18_FULL_PRICE = BigDecimal.valueOf(90);

    public static final BigDecimal HOUR_18_20_HALF_PRICE = BigDecimal.valueOf(80);
    public static final BigDecimal HOUR_18_20_FULL_PRICE = BigDecimal.valueOf(150);

    public static final BigDecimal HOUR_20_22_HALF_PRICE = BigDecimal.valueOf(60);
    public static final BigDecimal HOUR_20_22_FULL_PRICE = BigDecimal.valueOf(100);

    public static BigDecimal calPriceByHourAndIsSubCourt(Integer hour, Boolean isSubCourt) {
        if (HOUR_9.hour() <= hour && hour < HOUR_18.hour()) {
            return isSubCourt ? HOUR_9_18_HALF_PRICE : HOUR_9_18_FULL_PRICE;
        } else if (HOUR_18.hour() <= hour && hour < HOUR_20.hour()) {
            return isSubCourt ? HOUR_18_20_HALF_PRICE : HOUR_18_20_FULL_PRICE;
        } else if (HOUR_20.hour() <= hour && hour < HOUR_22.hour()) {
            return isSubCourt ? HOUR_20_22_HALF_PRICE : HOUR_20_22_FULL_PRICE;
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal calOrderOriginalAmount(OrderResponse response) {
        return response.getCourts().stream()
                .map(CourtBookingStatus::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
