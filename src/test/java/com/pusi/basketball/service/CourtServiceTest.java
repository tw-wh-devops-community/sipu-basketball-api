package com.pusi.basketball.service;

import com.pusi.basketball.controller.response.CourtStatus;
import com.pusi.basketball.model.Court;
import com.pusi.basketball.repository.CourtRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CourtServiceTest {

    @InjectMocks
    private CourtService courtService;

    @Mock
    private CourtBookingService courtBookingService;

    @Mock
    private CourtRepository courtRepository;

    @Test
    public void should_get_courts_with_status_when_all_courts_are_available() {
        String mockDate = "2021-04-06";
        Integer mockStartTime = 9;
        Integer mockEndTime = 12;
        doReturn(Collections.emptyList()).when(courtBookingService).findCourtBookingRecordOfGivenTimePeriod(mockDate, mockStartTime, mockEndTime);
        Court courtA1 = new Court();
        courtA1.setId(1L);
        courtA1.setCourt("A");
        courtA1.setSubCourt(1);
        doReturn(Collections.singletonList(courtA1)).when(courtRepository).findAll();

        List<CourtStatus> courtStatusList = courtService.getCourtsStatus(mockDate, mockStartTime, mockEndTime);

        assertEquals(1, courtStatusList.size());
        assertEquals("A", courtStatusList.get(0).getCourt());
        assertEquals(1, courtStatusList.get(0).getSubCourt());
        assertEquals(true, courtStatusList.get(0).getIsAvailable());
    }

}