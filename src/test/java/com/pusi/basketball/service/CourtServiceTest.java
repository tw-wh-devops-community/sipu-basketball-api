package com.pusi.basketball.service;

import com.pusi.basketball.controller.response.CourtStatus;
import com.pusi.basketball.model.Court;
import com.pusi.basketball.model.CourtBooking;
import com.pusi.basketball.repository.CourtRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class CourtServiceTest {

    @InjectMocks
    private CourtService courtService;

    @Mock
    private CourtRepository courtRepository;

    @Mock
    private CourtBookingService courtBookingService;

    @Test
    public void should_return_courts_with_available_status() {
        LocalDate mockDate = LocalDate.now();
        Integer mockStartTime = 9;
        Integer mockEndTime = 10;
        Court court1 = new Court();
        court1.setId(1L);
        court1.setCourt("A");
        court1.setSubCourt(0);
        Court court2 = new Court();
        court2.setId(2L);
        court2.setCourt("A");
        court2.setSubCourt(1);
        doReturn(Arrays.asList(court1, court2)).when(courtRepository).findAll();
        doReturn(Collections.emptyList()).when(courtBookingService)
                .findCourtBookingRecordOfGivenTimePeriod(mockDate, mockStartTime, mockEndTime);

        List<CourtStatus> courtsStatus = courtService.getCourtsStatus(mockDate, mockStartTime, mockEndTime);

        assertEquals(2, courtsStatus.size());
        assertTrue(courtsStatus.get(0).getIsAvailable());
        assertEquals(1L, courtsStatus.get(0).getId());
        assertEquals("A", courtsStatus.get(0).getCourt());
        assertEquals(0, courtsStatus.get(0).getSubCourt());
        assertTrue(courtsStatus.get(1).getIsAvailable());
        assertEquals(2L, courtsStatus.get(1).getId());
        assertEquals("A", courtsStatus.get(1).getCourt());
        assertEquals(1, courtsStatus.get(1).getSubCourt());
    }

    @Test
    public void should_return_courts_with_not_available_status() {
        LocalDate mockDate = LocalDate.now();
        Integer mockStartTime = 9;
        Integer mockEndTime = 10;
        Court court1 = new Court();
        court1.setId(1L);
        court1.setCourt("A");
        court1.setSubCourt(0);
        Court court2 = new Court();
        court2.setId(2L);
        court2.setCourt("A");
        court2.setSubCourt(1);
        doReturn(Arrays.asList(court1, court2)).when(courtRepository).findAll();
        CourtBooking courtBooking1 = new CourtBooking();
        courtBooking1.setCourt(court1);
        CourtBooking courtBooking2 = new CourtBooking();
        courtBooking2.setCourt(court2);
        doReturn(Arrays.asList(courtBooking1, courtBooking2)).when(courtBookingService)
                .findCourtBookingRecordOfGivenTimePeriod(mockDate, mockStartTime, mockEndTime);

        List<CourtStatus> courtsStatus = courtService.getCourtsStatus(mockDate, mockStartTime, mockEndTime);

        assertEquals(2, courtsStatus.size());
        assertFalse(courtsStatus.get(0).getIsAvailable());
        assertEquals(1L, courtsStatus.get(0).getId());
        assertEquals("A", courtsStatus.get(0).getCourt());
        assertEquals(0, courtsStatus.get(0).getSubCourt());
        assertFalse(courtsStatus.get(1).getIsAvailable());
        assertEquals(2L, courtsStatus.get(1).getId());
        assertEquals("A", courtsStatus.get(1).getCourt());
        assertEquals(1, courtsStatus.get(1).getSubCourt());
    }

    @Test
    public void should_return_courts_with_both_available_and_not_available_status() {
        LocalDate mockDate = LocalDate.now();
        Integer mockStartTime = 9;
        Integer mockEndTime = 10;
        Court court1 = new Court();
        court1.setId(1L);
        court1.setCourt("A");
        court1.setSubCourt(0);
        Court court2 = new Court();
        court2.setId(2L);
        court2.setCourt("A");
        court2.setSubCourt(1);
        doReturn(Arrays.asList(court1, court2)).when(courtRepository).findAll();
        CourtBooking courtBooking1 = new CourtBooking();
        courtBooking1.setCourt(court1);
        doReturn(Collections.singletonList(courtBooking1)).when(courtBookingService)
                .findCourtBookingRecordOfGivenTimePeriod(mockDate, mockStartTime, mockEndTime);

        List<CourtStatus> courtsStatus = courtService.getCourtsStatus(mockDate, mockStartTime, mockEndTime);

        assertEquals(2, courtsStatus.size());
        assertFalse(courtsStatus.get(0).getIsAvailable());
        assertEquals(1L, courtsStatus.get(0).getId());
        assertEquals("A", courtsStatus.get(0).getCourt());
        assertEquals(0, courtsStatus.get(0).getSubCourt());
        assertTrue(courtsStatus.get(1).getIsAvailable());
        assertEquals(2L, courtsStatus.get(1).getId());
        assertEquals("A", courtsStatus.get(1).getCourt());
        assertEquals(1, courtsStatus.get(1).getSubCourt());
    }

    @Test
    public void should_return_empty_array_when_no_courts() {
        LocalDate mockDate = LocalDate.now();
        Integer mockStartTime = 9;
        Integer mockEndTime = 10;
        doReturn(Collections.emptyList()).when(courtRepository).findAll();

        List<CourtStatus> courtsStatus = courtService.getCourtsStatus(mockDate, mockStartTime, mockEndTime);

        assertEquals(0, courtsStatus.size());
    }
}
