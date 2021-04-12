package com.pusi.basketball.service;

import com.pusi.basketball.controller.response.CourtBookingStatus;
import com.pusi.basketball.controller.response.OrderResponse;
import com.pusi.basketball.type.Coupon;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DiscountService {

    public static final BigDecimal COUPON_DISCOUNT_15 = BigDecimal.valueOf(-15);
    public static final BigDecimal COUPON_DISCOUNT_30 = BigDecimal.valueOf(-30);
    public static final BigDecimal COUPON_DISCOUNT_60 = BigDecimal.valueOf(-60);

    public static final BigDecimal OFF_5 = BigDecimal.valueOf(0.05);
    public static final BigDecimal OFF_10 = BigDecimal.valueOf(0.10);
    public static final BigDecimal OFF_15 = BigDecimal.valueOf(0.15);
    public static final BigDecimal OFF_20 = BigDecimal.valueOf(0.20);
    public static final BigDecimal OFF_30 = BigDecimal.valueOf(0.30);


    public static void calOrderPrice(OrderResponse response) {
        Integer orderPeriodHour = findOrderPeriodHour(response);
        response.setTimeDiscount(
                getTimeDiscountByPeriodHour(
                        orderPeriodHour, response.getOriginalAmount()
                )
        );
//        String coupon = response.getCoupon();


        response.setCouponDiscount(getCouponDiscount(response));
    }

    public static BigDecimal getCouponDiscount(OrderResponse response) {
        String coupon = response.getCoupon();
        BigDecimal amountTime = response.getTimeDiscount().add(response.getOriginalAmount());

        if (coupon.startsWith(Coupon.DIS_10.toString())) {
            return amountTime.multiply(OFF_10).negate();
        } else if (coupon.startsWith(Coupon.DIS_15.toString())) {
            return amountTime.multiply(OFF_15).negate();
        } else if (coupon.startsWith(Coupon.OFF_30.toString())) {
            return COUPON_DISCOUNT_30;
        } else if (coupon.startsWith(Coupon.OFF_60.toString())) {
            return COUPON_DISCOUNT_60;
        } else if (coupon.startsWith(Coupon.N_DIS_15.toString())) {
            response.setTimeDiscount(BigDecimal.ZERO);
            return response.getOriginalAmount().multiply(OFF_15).negate();
        } else if (coupon.startsWith(Coupon.N_DIS_30.toString())) {
            response.setTimeDiscount(BigDecimal.ZERO);
            return response.getOriginalAmount().multiply(OFF_30).negate();
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal getTimeDiscountByPeriodHour(Integer orderPeriodHour, BigDecimal originalAmount) {
        if (1 == orderPeriodHour) {
            return BigDecimal.ZERO;
        } else if (2 == orderPeriodHour) {
            return originalAmount.multiply(OFF_5).negate();
        } else if (3 == orderPeriodHour) {
            return originalAmount.multiply(OFF_10).negate();
        } else if (4 == orderPeriodHour) {
            return originalAmount.multiply(OFF_15).negate();
        } else {
            return originalAmount.multiply(OFF_20).negate();
        }
    }

    public static void calAmount(OrderResponse response) {
        BigDecimal amount = response.getOriginalAmount()
                .add(response.getCouponDiscount()).add(response.getTimeDiscount());
        response.setAmount(amount.compareTo(BigDecimal.valueOf(0)) < 0 ? BigDecimal.ZERO : amount);
    }

    public static Integer findOrderPeriodHour(OrderResponse response) {
        List<CourtBookingStatus> courtList = response.getCourts();
        return courtList.get(0).getPeriodHour();
    }
}
