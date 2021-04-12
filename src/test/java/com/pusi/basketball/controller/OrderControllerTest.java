package com.pusi.basketball.controller;

import com.pusi.basketball.controller.request.OrderCouponDto;
import com.pusi.basketball.controller.request.OrderDto;
import com.pusi.basketball.controller.response.OrderResponse;
import com.pusi.basketball.exception.ResourceNotFoundException;
import com.pusi.basketball.model.Order;
import com.pusi.basketball.service.CourtBookingService;
import com.pusi.basketball.service.OrderService;
import com.pusi.basketball.service.RequestParamValidatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private RequestParamValidatorService validatorService;

    @Mock
    private CourtBookingService courtBookingService;

    @Test
    void should_get_order_by_id() throws ResourceNotFoundException {
        Order order = new Order();
        order.setId(1L);
        OrderResponse response = new OrderResponse();
        response.setOrderId(1L);
        doReturn(order).when(orderService).getOrderById(1L);
        doReturn(response).when(orderService).getOrderResponse(order);
        ResponseEntity<OrderResponse> responseEntity = orderController.getOrderById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getOrderId());
    }

    @Test
    void update_order_coupon() throws ResourceNotFoundException {
        Order order = new Order();
        order.setId(1L);

        OrderResponse response = new OrderResponse();
        response.setCoupon("DIS_10");

        OrderCouponDto dto = new OrderCouponDto();
        dto.setCoupon("DIS_10");

        doReturn(order).when(orderService).getOrderById(1L);
        doReturn(response).when(orderService).getOrderResponse(order);
        ResponseEntity<OrderResponse> responseEntity = orderController.updateOrderCoupon(1L, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("DIS_10", responseEntity.getBody().getCoupon());
    }

    @Test
    void confirm_order_by_id() throws ResourceNotFoundException {
        Order order = new Order();
        order.setId(1L);

        doReturn(order).when(orderService).getOrderById(1L);
        ResponseEntity<String> responseEntity = orderController.confirmOrderById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success!", responseEntity.getBody());
    }

    @Test
    void should_throw_not_found_exception() throws ResourceNotFoundException {
        doThrow(new ResourceNotFoundException("Order 1 Not Found")).when(orderService).getOrderById(1L);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> orderController.getOrderById(1L));
        assertEquals("Order 1 Not Found", exception.getMessage());
    }

    @Test
    void should_response_order_id_when_create_draft_order_successfully() {
        OrderDto mockOrderDto = new OrderDto();
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        doReturn(mockOrder).when(orderService).createDraftOrder();
        doNothing().when(courtBookingService).createCourtBookings(mockOrder.getId(), mockOrderDto);
        doReturn(true).when(validatorService).validateCreateOrderRequestBody(any());

        ResponseEntity<OrderResponse> createOrderResponse = orderController.createOrder(mockOrderDto);

        assertEquals(HttpStatus.OK, createOrderResponse.getStatusCode());
        OrderResponse orderResponse = createOrderResponse.getBody();
        assertNotNull(orderResponse);
        assertEquals(mockOrder.getId(), orderResponse.getOrderId());
    }

    @Test
    void should_response_bad_request_when_create_draft_order_with_invalid_request_body() {
        OrderDto mockOrderDto = new OrderDto();
        doReturn(false).when(validatorService).validateCreateOrderRequestBody(any());

        ResponseEntity<OrderResponse> createOrderResponse = orderController.createOrder(mockOrderDto);

        assertEquals(HttpStatus.BAD_REQUEST, createOrderResponse.getStatusCode());
    }
}
