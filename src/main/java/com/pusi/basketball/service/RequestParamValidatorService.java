package com.pusi.basketball.service;

import com.pusi.basketball.controller.request.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RequestParamValidatorService {
    private static final Logger LOG = LoggerFactory.getLogger(RequestParamValidatorService.class);

    private static final Integer OPEN_TIME = 9;
    private static final Integer CLOSE_TIME = 22;

    private final CourtBookingService courtBookingService;

    public RequestParamValidatorService(final CourtBookingService courtBookingService) {
        this.courtBookingService = courtBookingService;
    }

    public boolean validateGetCourtsRequestParam(LocalDate date, Integer startTime, Integer endTime) {
        return !date.isBefore(LocalDate.now())
                && startTime >= OPEN_TIME && startTime < CLOSE_TIME
                && endTime > OPEN_TIME && endTime <= CLOSE_TIME
                && startTime < endTime;
    }

    public boolean validateCreateOrderRequestBody(OrderDto orderDto) {
        return false;
    }
}
