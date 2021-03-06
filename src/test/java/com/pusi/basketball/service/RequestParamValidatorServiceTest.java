package com.pusi.basketball.service;

import com.pusi.basketball.controller.request.OrderDto;
import com.pusi.basketball.model.Court;
import com.pusi.basketball.model.CourtBooking;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RequestParamValidatorServiceTest {

    @InjectMocks
    private RequestParamValidatorService validatorService;

    @Mock
    private CourtBookingService courtBookingService;

    @Test
    void should_return_true_when_get_courts_request_params_are_valid() {
        LocalDate mockDate = LocalDate.now();
        Integer mockStartTime = 9;
        Integer mockEndTime = 12;

        assertTrue(validatorService.validateGetCourtsRequestParam(mockDate, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_past_date() {
        LocalDate mockDate = LocalDate.now().plusDays(-1);
        Integer mockStartTime = 9;
        Integer mockEndTime = 12;

        assertFalse(validatorService.validateGetCourtsRequestParam(mockDate, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_start_time_before_9() {
        LocalDate mockDate = LocalDate.now();
        Integer mockStartTime = 8;
        Integer mockEndTime = 12;

        assertFalse(validatorService.validateGetCourtsRequestParam(mockDate, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_start_time_after_21() {
        LocalDate mockDate = LocalDate.now();
        Integer mockStartTime = 22;
        Integer mockEndTime = 12;

        assertFalse(validatorService.validateGetCourtsRequestParam(mockDate, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_end_time_before_10() {
        LocalDate mockDate = LocalDate.now();
        Integer mockStartTime = 9;
        Integer mockEndTime = 9;

        assertFalse(validatorService.validateGetCourtsRequestParam(mockDate, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_end_time_after_22() {
        LocalDate mockDate = LocalDate.now();
        Integer mockStartTime = 9;
        Integer mockEndTime = 23;

        assertFalse(validatorService.validateGetCourtsRequestParam(mockDate, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_start_time_not_before_end_time() {
        LocalDate mockDate = LocalDate.now();
        Integer mockStartTime = 12;
        Integer mockEndTime = 11;

        assertFalse(validatorService.validateGetCourtsRequestParam(mockDate, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_create_order_with_invalid_param_format() {
        OrderDto orderDto = new OrderDto();
        orderDto.setDate(LocalDate.now().plusDays(-1));

        assertFalse(validatorService.validateCreateOrderRequestBody(orderDto));
    }

    @Test
    void should_return_false_when_create_order_but_court_not_available() {
        LocalDate mockDate = LocalDate.now();
        Integer mockStartTime = 11;
        Integer mockEndTime = 12;
        OrderDto orderDto = new OrderDto();
        orderDto.setDate(mockDate);
        orderDto.setStartTime(mockStartTime);
        orderDto.setEndTime(mockEndTime);
        orderDto.setSelectedCourts(Collections.singletonList(1L));
        Court court = new Court();
        court.setId(1L);
        CourtBooking courtBooking = new CourtBooking();
        courtBooking.setCourt(court);
        doReturn(Collections.singletonList(courtBooking)).when(courtBookingService)
                .findCourtBookingRecordOfGivenTimePeriod(mockDate, mockStartTime, mockEndTime);

        assertFalse(validatorService.validateCreateOrderRequestBody(orderDto));
    }

    @Test
    void should_return_true_when_create_order_and_court_is_available() {
        LocalDate mockDate = LocalDate.now();
        Integer mockStartTime = 11;
        Integer mockEndTime = 12;
        OrderDto orderDto = new OrderDto();
        orderDto.setDate(mockDate);
        orderDto.setStartTime(mockStartTime);
        orderDto.setEndTime(mockEndTime);
        orderDto.setSelectedCourts(Collections.singletonList(1L));
        Court court = new Court();
        court.setId(2L);
        CourtBooking courtBooking = new CourtBooking();
        courtBooking.setCourt(court);
        doReturn(Collections.singletonList(courtBooking)).when(courtBookingService)
                .findCourtBookingRecordOfGivenTimePeriod(mockDate, mockStartTime, mockEndTime);

        assertTrue(validatorService.validateCreateOrderRequestBody(orderDto));
    }

}
