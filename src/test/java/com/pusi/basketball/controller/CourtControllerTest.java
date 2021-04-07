package com.pusi.basketball.controller;

import com.pusi.basketball.controller.response.CourtStatus;
import com.pusi.basketball.controller.response.CourtsStatusResponse;
import com.pusi.basketball.service.CourtService;
import com.pusi.basketball.service.RequestParamValidatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CourtControllerTest {
    @InjectMocks
    private CourtController controller;

    @Mock
    private CourtService courtService;

    @Mock
    private RequestParamValidatorService validatorService;

    @Test
    public void should_response_all_courts() {
        LocalDate mockDate = LocalDate.now();
        String mockDateStr = mockDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        Integer mockStartTime = 9;
        Integer mockEndTime = 12;
        CourtStatus courtStatus = new CourtStatus();
        List<CourtStatus> courtStatusList = Collections.singletonList(courtStatus);
        doReturn(true).when(validatorService).validateGetCourtsRequestParam(mockDateStr, mockStartTime, mockEndTime);
        doReturn(courtStatusList).when(courtService).getCourtsStatus(mockDate, mockStartTime, mockEndTime);

        ResponseEntity<CourtsStatusResponse> response = controller.getCourts(mockDateStr, mockStartTime, mockEndTime);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        CourtsStatusResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(mockDate.format(DateTimeFormatter.ISO_LOCAL_DATE), body.getDate());
        assertEquals(mockStartTime, body.getStartTime());
        assertEquals(mockEndTime, body.getEndTime());
        assertEquals(1, body.getCourts().size());
    }

    @Test
    public void should_response_bad_request_when_date_query_param_is_not_valid() {
        LocalDate mockDate = LocalDate.now();
        String mockDateStr = mockDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        Integer mockStartTime = 9;
        Integer mockEndTime = 12;
        doReturn(false).when(validatorService).validateGetCourtsRequestParam(mockDateStr, mockStartTime, mockEndTime);

        ResponseEntity<CourtsStatusResponse> response = controller.getCourts(mockDateStr, mockStartTime, mockEndTime);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

}
