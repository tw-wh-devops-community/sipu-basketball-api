package com.pusi.basketball.service;

import com.pusi.basketball.controller.response.CourtBookingStatus;
import com.pusi.basketball.controller.response.OrderResponse;
import com.pusi.basketball.exception.ResourceNotFoundException;
import com.pusi.basketball.model.Order;
import com.pusi.basketball.repository.OrderRepository;
import com.pusi.basketball.service.dto.CourtBookingDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private CourtBookingService courtBookingService;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void should_return_exists_order() throws ResourceNotFoundException {
        Order mockOrder = new Order();
        mockOrder.setConfirm(false);
        mockOrder.setId(1L);
        doReturn(Optional.of(mockOrder)).when(orderRepository).findOrderByIdAndConfirmIsFalse(1L);

        Order order = orderService.getOrderById(1L);

        assertEquals(1L, order.getId());
    }

    @Test
    void should_not_return_exists_order() throws ResourceNotFoundException {
        doReturn(Optional.empty()).when(orderRepository).findOrderByIdAndConfirmIsFalse(1L);

        try {
            Order order = orderService.getOrderById(1L);
        } catch (Exception e) {
            assertEquals("Order 1 not found", e.getMessage());
            return;
        }

        assertEquals(1, -1, "not run here, must throw an exception.");
    }

    @Test
    void should_update_order_coupon() {
        Order order = new Order();
        doReturn(order).when(orderRepository).save(order);
        orderService.updateOrderCoupon(order, "123456");

        assertEquals("123456", order.getCoupon());
    }

    @Test
    void should_confirm_order() {
        CourtBookingStatus courtBookingStatus = new CourtBookingStatus();
        courtBookingStatus.setPeriodHour(1);
        courtBookingStatus.setAmount(BigDecimal.TEN);

        Order order = new Order();
        order.setId(1L);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setAmount(BigDecimal.TEN);
        List<CourtBookingDto> dtoList = Collections.emptyList();
        List<CourtBookingStatus> statusList = Collections.singletonList(courtBookingStatus);
        doReturn(dtoList).when(courtBookingService).getCourtBookingsByOrderId(1L);
        doReturn(statusList).when(courtBookingService).calPriceByCourtBookingDto(dtoList);

        orderService.confirmOrder(order);

        assertEquals(true, order.getConfirm());
    }

    @Test
    void createDraftOrder() {
    }

    @Test
    void should_gene_order_response() {
        Order order = new Order();
        order.setId(1L);

        CourtBookingDto dto = new CourtBookingDto();
        dto.setCourt("A");
        dto.setSubCourt(1);
        dto.setBookingHour(10);
        List<CourtBookingDto> dtoList = Collections.singletonList(dto);

        CourtBookingStatus status = new CourtBookingStatus();
        status.setCourt("A");
        status.setSubCourt(1);
        status.setPeriodHour(1);
        status.setAmount(BigDecimal.valueOf(50));
        List<CourtBookingStatus> statusList = Collections.singletonList(status);

        doReturn(dtoList).when(courtBookingService).getCourtBookingsByOrderId(1L);
        doReturn(statusList).when(courtBookingService).calPriceByCourtBookingDto(dtoList);

        OrderResponse orderResponse = orderService.getOrderResponse(order);

        assertEquals(1, orderResponse.getCourts().size());
        assertEquals("A", orderResponse.getCourts().get(0).getCourt());
        assertEquals(1, orderResponse.getCourts().get(0).getSubCourt());
        assertEquals(1, orderResponse.getCourts().get(0).getPeriodHour());
        assertEquals(BigDecimal.valueOf(50), orderResponse.getCourts().get(0).getAmount());
        assertEquals(BigDecimal.valueOf(50), orderResponse.getOriginalAmount());
    }

    @Test
    void check_not_a_coupon() {
        try {
            OrderService.checkCoupon("XXXX");
        } catch (Exception e) {
            assertEquals("Not Allow Coupon", e.getMessage());
            return;
        }
        assertEquals(1, -1);
    }

    @Test
    void check_a_coupon() {
        try {
            OrderService.checkCoupon("DIS_10_22222");
        } catch (Exception e) {
            assertEquals(1, -1);
        }
    }
}
