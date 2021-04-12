package com.pusi.basketball.controller;

import com.pusi.basketball.controller.request.OrderCouponDto;
import com.pusi.basketball.controller.request.OrderDto;
import com.pusi.basketball.controller.response.OrderResponse;
import com.pusi.basketball.exception.ResourceNotFoundException;
import com.pusi.basketball.model.Order;
import com.pusi.basketball.service.CourtBookingService;
import com.pusi.basketball.service.OrderService;
import com.pusi.basketball.service.RequestParamValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("orders")
public class OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final CourtBookingService courtBookingService;
    private final RequestParamValidatorService validatorService;

    public OrderController(
            final OrderService orderService,
            final CourtBookingService courtBookingService,
            final RequestParamValidatorService validatorService) {
        this.orderService = orderService;
        this.courtBookingService = courtBookingService;
        this.validatorService = validatorService;
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.getOrderResponse(orderService.getOrderById(orderId)));
    }

    @PutMapping("/{orderId}/coupon")
    public ResponseEntity<OrderResponse> updateOrderCoupon(
            @PathVariable Long orderId, @RequestBody @Valid OrderCouponDto orderCouponDto
    ) throws ResourceNotFoundException {
        OrderService.checkCoupon(orderCouponDto.getCoupon());
        Order order = orderService.getOrderById(orderId);
        orderService.updateOrderCoupon(order, orderCouponDto.getCoupon());
        return ResponseEntity.ok(orderService.getOrderResponse(order));
    }

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<String> confirmOrderById(@PathVariable Long orderId) throws ResourceNotFoundException {
        Order order = orderService.getOrderById(orderId);
        orderService.confirmOrder(order);
        return ResponseEntity.ok("Success!");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderDto orderDto) {
        if (!validatorService.validateCreateOrderRequestBody(orderDto)) {
            return ResponseEntity.badRequest().body(null);
        }

        Order order = orderService.createDraftOrder();
        courtBookingService.createCourtBookings(order.getId(), orderDto);

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        return ResponseEntity.ok().body(response);
    }
}
