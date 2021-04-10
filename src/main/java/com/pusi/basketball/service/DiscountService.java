package com.pusi.basketball.service;

import com.pusi.basketball.controller.response.OrderResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountService {

    public static final BigDecimal COUPON_DISCOUNT_15 = BigDecimal.valueOf(-15);
    public static final BigDecimal COUPON_DISCOUNT_30 = BigDecimal.valueOf(-30);
    public static final BigDecimal COUPON_DISCOUNT_60 = BigDecimal.valueOf(-60);

    public static final BigDecimal OFF_5 = BigDecimal.valueOf(0.05);
    public static final BigDecimal OFF_10 = BigDecimal.valueOf(0.10);
    public static final BigDecimal OFF_15 = BigDecimal.valueOf(0.15);
    public static final BigDecimal OFF_20 = BigDecimal.valueOf(0.20);


    public static void calOrderPrice(OrderResponse response) {
        // todo
    }
}
