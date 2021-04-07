package com.pusi.basketball.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class RequestParamValidatorServiceTest {

    @InjectMocks
    private RequestParamValidatorService validatorService;

    @Test
    void should_return_true_when_get_courts_request_params_are_valid() {
        LocalDate mockDate = LocalDate.now();
        String mockDateStr = mockDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        Integer mockStartTime = 9;
        Integer mockEndTime = 12;

        assertTrue(validatorService.validateGetCourtsRequestParam(mockDateStr, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_invalid_format_date() {
        Integer mockStartTime = 9;
        Integer mockEndTime = 12;

        assertFalse(validatorService.validateGetCourtsRequestParam("22220101", mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_past_date() {
        LocalDate mockDate = LocalDate.now().plusDays(-1);
        String mockDateStr = mockDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        Integer mockStartTime = 9;
        Integer mockEndTime = 12;

        assertFalse(validatorService.validateGetCourtsRequestParam(mockDateStr, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_start_time_before_9() {
        LocalDate mockDate = LocalDate.now();
        String mockDateStr = mockDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        Integer mockStartTime = 8;
        Integer mockEndTime = 12;

        assertFalse(validatorService.validateGetCourtsRequestParam(mockDateStr, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_start_time_after_21() {
        LocalDate mockDate = LocalDate.now();
        String mockDateStr = mockDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        Integer mockStartTime = 22;
        Integer mockEndTime = 12;

        assertFalse(validatorService.validateGetCourtsRequestParam(mockDateStr, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_end_time_before_10() {
        LocalDate mockDate = LocalDate.now();
        String mockDateStr = mockDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        Integer mockStartTime = 9;
        Integer mockEndTime = 9;

        assertFalse(validatorService.validateGetCourtsRequestParam(mockDateStr, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_end_time_after_22() {
        LocalDate mockDate = LocalDate.now();
        String mockDateStr = mockDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        Integer mockStartTime = 9;
        Integer mockEndTime = 23;

        assertFalse(validatorService.validateGetCourtsRequestParam(mockDateStr, mockStartTime, mockEndTime));
    }

    @Test
    void should_return_false_when_get_courts_with_start_time_not_before_end_time() {
        LocalDate mockDate = LocalDate.now();
        String mockDateStr = mockDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        Integer mockStartTime = 12;
        Integer mockEndTime = 11;

        assertFalse(validatorService.validateGetCourtsRequestParam(mockDateStr, mockStartTime, mockEndTime));
    }

}
