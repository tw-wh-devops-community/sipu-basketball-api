package com.pusi.basketball.controller;

import com.pusi.basketball.controller.response.CourtStatus;
import com.pusi.basketball.controller.response.CourtsStatusResponse;
import com.pusi.basketball.service.CourtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CourtControllerTest {
    @InjectMocks
    private CourtController controller;

    @Mock
    private CourtService courtService;

    @Test
    public void should_response_all_courts() {
        String mockDate = "2021-04-06";
        Integer mockStartTime = 9;
        Integer mockEndTime = 12;
        CourtStatus courtStatus = new CourtStatus();
        List<CourtStatus> courtStatusList = Collections.singletonList(courtStatus);
        doReturn(courtStatusList).when(courtService).getCourtsStatus(mockDate, mockStartTime, mockEndTime);

        ResponseEntity<CourtsStatusResponse> response = controller.getCourts(mockDate, mockStartTime, mockEndTime);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        CourtsStatusResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(mockDate, body.getDate());
        assertEquals(mockStartTime, body.getStartTime());
        assertEquals(mockEndTime, body.getEndTime());
        assertEquals(1, body.getCourts().size());
    }

}