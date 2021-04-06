package com.pusi.basketball.service;

import com.pusi.basketball.model.CourtBooking;
import com.pusi.basketball.repository.CourtBookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CourtBookingServiceTest {

    @InjectMocks
    private CourtBookingService courtBookingService;

    @Mock
    private CourtBookingRepository courtBookingRepository;

    @Test
    public void should_find_booking_records_by_date_and_hours() {
        LocalDate date = LocalDate.now();
        CourtBooking courtBooking = new CourtBooking();
        courtBooking.setId(1L);
        doReturn(Collections.singletonList(courtBooking)).when(courtBookingRepository)
                .findCourtBookingByDateAndHourBetween(date, 9, 12);

        List<CourtBooking> courtBookings = courtBookingService.findCourtBookingRecordOfGivenTimePeriod(
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), 9, 12);

        assertEquals(1, courtBookings.size());
        assertEquals(1L, courtBookings.get(0).getId());
    }

}
