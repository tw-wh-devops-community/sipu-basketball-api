package com.pusi.basketball.service;

import com.pusi.basketball.controller.response.CourtBookingStatus;
import com.pusi.basketball.controller.response.OrderResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountServiceTest {

    CourtBookingStatus geneCourtStatus(String court, Integer subCourt, Integer amount, Integer periodHour) {
        CourtBookingStatus status = new CourtBookingStatus();
        status.setCourt(court);
        status.setSubCourt(subCourt);
        status.setPeriodHour(periodHour);
        status.setAmount(new BigDecimal(amount.toString()));
        return status;
    }

    @Test
    public void should_return_1_hour_period_hour_from_order_response() {
        OrderResponse response = new OrderResponse();
        CourtBookingStatus status = geneCourtStatus("A", 1, 100, 1);
        List<CourtBookingStatus> statusList = Collections.singletonList(status);
        response.setCourts(statusList);

        assertEquals(1, DiscountService.findOrderPeriodHour(response));
    }

    @Test
    public void should_return_2_hours_period_hour_from_order_response() {
        OrderResponse response = new OrderResponse();
        CourtBookingStatus status = geneCourtStatus("A", 1, 100, 2);
        List<CourtBookingStatus> statusList = Collections.singletonList(status);
        response.setCourts(statusList);

        assertEquals(2, DiscountService.findOrderPeriodHour(response));
    }

    @Test
    public void should_return_3_hours_period_hour_from_order_response() {
        OrderResponse response = new OrderResponse();
        CourtBookingStatus status = geneCourtStatus("A", 1, 100, 3);
        List<CourtBookingStatus> statusList = Collections.singletonList(status);
        response.setCourts(statusList);

        assertEquals(3, DiscountService.findOrderPeriodHour(response));
    }

    @Test
    public void should_return_4_hours_period_hour_from_order_response() {
        OrderResponse response = new OrderResponse();
        CourtBookingStatus status = geneCourtStatus("A", 1, 100, 4);
        List<CourtBookingStatus> statusList = Collections.singletonList(status);
        response.setCourts(statusList);

        assertEquals(4, DiscountService.findOrderPeriodHour(response));
    }

    @Test
    public void should_return_5_hours_period_hour_from_order_response() {
        OrderResponse response = new OrderResponse();
        CourtBookingStatus status = geneCourtStatus("A", 1, 100, 5);
        List<CourtBookingStatus> statusList = Collections.singletonList(status);
        response.setCourts(statusList);

        assertEquals(5, DiscountService.findOrderPeriodHour(response));
    }

    @Test
    public void should_return_1_hour_discount_amount() {
        BigDecimal timeDiscount = DiscountService.getTimeDiscountByPeriodHour(1, BigDecimal.valueOf(100));

        assertEquals(0, BigDecimal.valueOf(-0).compareTo(timeDiscount));
    }

    @Test
    public void should_return_2_hours_discount_amount() {
        BigDecimal timeDiscount = DiscountService.getTimeDiscountByPeriodHour(2, BigDecimal.valueOf(100));

        assertEquals(0, BigDecimal.valueOf(-5).compareTo(timeDiscount));
    }

    @Test
    public void should_return_3_hours_discount_amount() {
        BigDecimal timeDiscount = DiscountService.getTimeDiscountByPeriodHour(3, BigDecimal.valueOf(100));

        assertEquals(0, BigDecimal.valueOf(-10).compareTo(timeDiscount));
    }

    @Test
    public void should_return_4_hours_discount_amount() {
        BigDecimal timeDiscount = DiscountService.getTimeDiscountByPeriodHour(4, BigDecimal.valueOf(100));

        assertEquals(0, BigDecimal.valueOf(-15).compareTo(timeDiscount));
    }

    @Test
    public void should_return_5_hours_discount_amount() {
        BigDecimal timeDiscount = DiscountService.getTimeDiscountByPeriodHour(5, BigDecimal.valueOf(100));

        assertEquals(0, BigDecimal.valueOf(-20).compareTo(timeDiscount));
    }

    @Test
    public void should_return_6_hours_discount_amount() {
        BigDecimal timeDiscount = DiscountService.getTimeDiscountByPeriodHour(6, BigDecimal.valueOf(100));

        assertEquals(0, BigDecimal.valueOf(-20).compareTo(timeDiscount));
    }

    @Test
    public void should_return_dis_10() {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOriginalAmount(BigDecimal.valueOf(100));
        orderResponse.setTimeDiscount(BigDecimal.valueOf(-20));
        orderResponse.setCoupon("DIS_10");
        BigDecimal couponDiscount = DiscountService.getCouponDiscount(orderResponse);
        assertEquals(0, BigDecimal.valueOf(-8).compareTo(couponDiscount));
    }

    @Test
    public void should_return_dis_15() {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOriginalAmount(BigDecimal.valueOf(100));
        orderResponse.setTimeDiscount(BigDecimal.valueOf(-20));
        orderResponse.setCoupon("DIS_15");
        BigDecimal couponDiscount = DiscountService.getCouponDiscount(orderResponse);
        assertEquals(0, BigDecimal.valueOf(-12).compareTo(couponDiscount));
    }

    @Test
    public void should_return_dis_30() {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOriginalAmount(BigDecimal.valueOf(100));
        orderResponse.setTimeDiscount(BigDecimal.valueOf(-20));
        orderResponse.setCoupon("OFF_30");
        BigDecimal couponDiscount = DiscountService.getCouponDiscount(orderResponse);
        assertEquals(0, BigDecimal.valueOf(-30).compareTo(couponDiscount));
    }

    @Test
    public void should_return_dis_60() {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOriginalAmount(BigDecimal.valueOf(100));
        orderResponse.setTimeDiscount(BigDecimal.valueOf(-20));
        orderResponse.setCoupon("OFF_60");
        BigDecimal couponDiscount = DiscountService.getCouponDiscount(orderResponse);
        assertEquals(0, BigDecimal.valueOf(-60).compareTo(couponDiscount));
    }

    @Test
    public void should_return_n_dis_15() {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOriginalAmount(BigDecimal.valueOf(100));
        orderResponse.setTimeDiscount(BigDecimal.valueOf(-20));
        orderResponse.setCoupon("N_DIS_15");
        BigDecimal couponDiscount = DiscountService.getCouponDiscount(orderResponse);
        assertEquals(0, BigDecimal.valueOf(-15).compareTo(couponDiscount));
        assertEquals(0, BigDecimal.valueOf(0).compareTo(orderResponse.getTimeDiscount()));
    }

    @Test
    public void should_return_n_dis_30() {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOriginalAmount(BigDecimal.valueOf(100));
        orderResponse.setTimeDiscount(BigDecimal.valueOf(-20));
        orderResponse.setCoupon("N_DIS_30");
        BigDecimal couponDiscount = DiscountService.getCouponDiscount(orderResponse);
        assertEquals(0, BigDecimal.valueOf(-30).compareTo(couponDiscount));
        assertEquals(0, BigDecimal.valueOf(0).compareTo(orderResponse.getTimeDiscount()));
    }


}
