package com.pusi.basketball.service;

import com.pusi.basketball.controller.response.CourtBookingStatus;
import com.pusi.basketball.controller.response.OrderResponse;
import com.pusi.basketball.exception.BadRequestException;
import com.pusi.basketball.exception.ResourceNotFoundException;
import com.pusi.basketball.model.Order;
import com.pusi.basketball.repository.OrderRepository;
import com.pusi.basketball.service.dto.CourtBookingDto;
import com.pusi.basketball.type.Coupon;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.pusi.basketball.service.DiscountService.calOrderPrice;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final CourtBookingService courtBookingService;

    public OrderService(final OrderRepository orderRepository, final CourtBookingService courtBookingService) {
        this.orderRepository = orderRepository;
        this.courtBookingService = courtBookingService;
    }

    public Order getOrderById(Long orderId) throws ResourceNotFoundException {
        Optional<Order> order = orderRepository.findOrderByIdAndConfirmIsFalse(orderId);
        if (order.isEmpty()) {
            throw new ResourceNotFoundException("Order " + orderId + " not found");
        }
        return order.get();
    }


    public void updateOrderCoupon(Order order, String coupon) {
        order.setCoupon(coupon);
        orderRepository.save(order);
    }

    public void confirmOrder(Order order) {
        order.setConfirm(true);
        order.setAmount(getOrderResponse(order).getAmount());
        orderRepository.save(order);
    }

    public Order createDraftOrder() {
        return orderRepository.save(new Order());
    }

    public OrderResponse getOrderResponse(Order order) {
        List<CourtBookingDto> courtBookings = courtBookingService.getCourtBookingsByOrderId(order.getId());
        List<CourtBookingStatus> courtBookingStatusList = courtBookingService.calPriceByCourtBookingDto(courtBookings);

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setCoupon(order.getCoupon());
        response.setCourts(courtBookingStatusList);
        response.setOriginalAmount(CourtPriceService.calOrderOriginalAmount(response));

        calOrderPrice(response);

        return response;
    }

    public static void checkCoupon(String coupon) {
        boolean flag = Arrays.stream(Coupon.values()).map(c -> coupon.startsWith(c.toString()))
                .reduce(false, (a, b) -> a || b);
        if (!flag) {
            throw new BadRequestException("Not Allow Coupon");
        }
    }
}
