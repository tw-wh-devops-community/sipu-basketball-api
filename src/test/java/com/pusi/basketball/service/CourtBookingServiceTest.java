package com.pusi.basketball.service;

import com.pusi.basketball.controller.response.CourtBookingStatus;
import com.pusi.basketball.model.Court;
import com.pusi.basketball.model.CourtBooking;
import com.pusi.basketball.repository.CourtBookingRepository;
import com.pusi.basketball.repository.CourtRepository;
import com.pusi.basketball.service.dto.CourtBookingDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourtBookingServiceTest {

    @InjectMocks
    private CourtBookingService courtBookingService;

    @Mock
    private CourtBookingRepository courtBookingRepository;

    @Mock
    private CourtRepository courtRepository;

    @Captor
    private ArgumentCaptor<List<CourtBooking>> courtBookingArgumentCaptor;

    @Test
    public void should_find_booking_records_by_date_and_hours() {
        LocalDate date = LocalDate.now();
        CourtBooking courtBooking = new CourtBooking();
        courtBooking.setId(1L);
        doReturn(Collections.singletonList(courtBooking)).when(courtBookingRepository)
                .findCourtBookingByDateAndHourBetween(date, 9, 12);

        List<CourtBooking> courtBookings = courtBookingService.findCourtBookingRecordOfGivenTimePeriod(
                date, 9, 12);

        assertEquals(1, courtBookings.size());
        assertEquals(1L, courtBookings.get(0).getId());
    }


    @Test
    void should_return_half_court_booking_dto() {
        CourtBooking courtBooking = geneBookingCourt("A", 1, 10);

        doReturn(Collections.singletonList(courtBooking)).when(courtBookingRepository)
                .findCourtBookingByOrderId(1L);

        List<CourtBookingDto> courtBookingDtos = courtBookingService.getCourtBookingsByOrderId(1L);

        assertEquals(1, courtBookingDtos.size());
        assertEquals("A", courtBookingDtos.get(0).getCourt());
        assertEquals(1, courtBookingDtos.get(0).getSubCourt());
    }

    @Test
    void should_return_full_court_booking_dto() {
        List<CourtBooking> courtBookings = new java.util.ArrayList<>(Collections.emptyList());
        courtBookings.add(geneBookingCourt("A", 1, 10));
        courtBookings.add(geneBookingCourt("A", 2, 10));
        doReturn(courtBookings).when(courtBookingRepository)
                .findCourtBookingByOrderId(1L);

        List<CourtBookingDto> courtBookingDtos = courtBookingService.getCourtBookingsByOrderId(1L);

        assertEquals(1, courtBookingDtos.size());
        assertEquals("A", courtBookingDtos.get(0).getCourt());
        assertNull(courtBookingDtos.get(0).getSubCourt());
        assertEquals(10, courtBookingDtos.get(0).getBookingHour());
    }

    @Test
    void should_return_2_half_courts_booking_dto() {
        List<CourtBooking> courtBookings = new java.util.ArrayList<>(Collections.emptyList());
        courtBookings.add(geneBookingCourt("A", 1, 10));
        courtBookings.add(geneBookingCourt("B", 2, 10));
        doReturn(courtBookings).when(courtBookingRepository)
                .findCourtBookingByOrderId(1L);

        List<CourtBookingDto> courtBookingDtos = courtBookingService.getCourtBookingsByOrderId(1L);
        courtBookingDtos.sort((ldto, rdto) -> {
            if (!ldto.getBookingHour().equals(rdto.getBookingHour())) {
                return ldto.getBookingHour() < rdto.getBookingHour() ? -1 : 1;
            }
            if (ldto.getCourt().charAt(0) == rdto.getCourt().charAt(0)) {
                return 0;
            } else {
                return ldto.getCourt().charAt(0) < rdto.getCourt().charAt(0) ? -1 : 1;
            }
        });

        assertEquals(2, courtBookingDtos.size());
        assertEquals("A", courtBookingDtos.get(0).getCourt());
        assertEquals(1, courtBookingDtos.get(0).getSubCourt());
        assertEquals(10, courtBookingDtos.get(0).getBookingHour());
        assertEquals("B", courtBookingDtos.get(1).getCourt());
        assertEquals(2, courtBookingDtos.get(1).getSubCourt());
        assertEquals(10, courtBookingDtos.get(1).getBookingHour());
    }

    @Test
    void should_return_2_hours_half_court_booking_dto() {
        List<CourtBooking> courtBookings = new java.util.ArrayList<>(Collections.emptyList());
        courtBookings.add(geneBookingCourt("A", 1, 10));
        courtBookings.add(geneBookingCourt("A", 1, 11));
        doReturn(courtBookings).when(courtBookingRepository)
                .findCourtBookingByOrderId(1L);

        List<CourtBookingDto> courtBookingDtos = courtBookingService.getCourtBookingsByOrderId(1L);
        courtBookingDtos.sort((ldto, rdto) -> {
            if (!ldto.getBookingHour().equals(rdto.getBookingHour())) {
                return ldto.getBookingHour() < rdto.getBookingHour() ? -1 : 1;
            }
            if (ldto.getCourt().charAt(0) == rdto.getCourt().charAt(0)) {
                return 0;
            } else {
                return ldto.getCourt().charAt(0) < rdto.getCourt().charAt(0) ? -1 : 1;
            }
        });

        assertEquals(2, courtBookingDtos.size());
        assertEquals("A", courtBookingDtos.get(0).getCourt());
        assertEquals(1, courtBookingDtos.get(0).getSubCourt());
        assertEquals(10, courtBookingDtos.get(0).getBookingHour());
        assertEquals("A", courtBookingDtos.get(1).getCourt());
        assertEquals(1, courtBookingDtos.get(1).getSubCourt());
        assertEquals(11, courtBookingDtos.get(1).getBookingHour());
    }

    private CourtBooking geneBookingCourt(String courtName, Integer subCourt, Integer hour) {
        CourtBooking courtBooking = new CourtBooking();
        courtBooking.setCourt(geneCourt(courtName, subCourt));
        courtBooking.setHour(hour);
        return courtBooking;
    }

    private Court geneCourt(String courtName, Integer subCourt) {
        Court court = new Court();
        court.setCourt(courtName);
        court.setSubCourt(subCourt);
        return court;
    }

    private CourtBookingDto geneCourtBookingDto(String court, Integer subCourt, Integer bookingHour) {
        CourtBookingDto dto = new CourtBookingDto();
        dto.setCourt(court);
        dto.setSubCourt(subCourt);
        dto.setBookingHour(bookingHour);
        return dto;
    }

    @Test
    void should_return_1_half_court_booking_status_with_amount() {
        List<CourtBookingDto> dtos = new ArrayList<>();
        dtos.add(geneCourtBookingDto("A", 1, 10));

        List<CourtBookingStatus> statusList = courtBookingService.calPriceByCourtBookingDto(dtos);

        assertEquals(1, statusList.size());
        assertEquals("A", statusList.get(0).getCourt());
        assertEquals(1, statusList.get(0).getSubCourt());
        assertEquals(1, statusList.get(0).getPeriodHour());
        assertEquals(BigDecimal.valueOf(50), statusList.get(0).getAmount());
    }

    @Test
    void should_return_full_court_1_hour_booking_status_with_amount() {
        List<CourtBookingDto> dtos = new ArrayList<>();
        dtos.add(geneCourtBookingDto("A", null, 10));

        List<CourtBookingStatus> statusList = courtBookingService.calPriceByCourtBookingDto(dtos);

        assertEquals(1, statusList.size());
        assertEquals("A", statusList.get(0).getCourt());
        assertNull(statusList.get(0).getSubCourt());
        assertEquals(1, statusList.get(0).getPeriodHour());
        assertEquals(BigDecimal.valueOf(90), statusList.get(0).getAmount());
    }

    @Test
    void should_return_half_court_2_hour_booking_status_with_amount() {
        List<CourtBookingDto> dtos = new ArrayList<>();
        dtos.add(geneCourtBookingDto("A", 1, 10));
        dtos.add(geneCourtBookingDto("A", 1, 11));

        List<CourtBookingStatus> statusList = courtBookingService.calPriceByCourtBookingDto(dtos);

        assertEquals(1, statusList.size());
        assertEquals("A", statusList.get(0).getCourt());
        assertEquals(1, statusList.get(0).getSubCourt());
        assertEquals(2, statusList.get(0).getPeriodHour());
        assertEquals(BigDecimal.valueOf(100), statusList.get(0).getAmount());
    }

    @Test
    void should_return_full_court_2_hour_booking_status_with_amount() {
        List<CourtBookingDto> dtos = new ArrayList<>();
        dtos.add(geneCourtBookingDto("A", null, 10));
        dtos.add(geneCourtBookingDto("A", null, 11));

        List<CourtBookingStatus> statusList = courtBookingService.calPriceByCourtBookingDto(dtos);

        assertEquals(1, statusList.size());
        assertEquals("A", statusList.get(0).getCourt());
        assertNull(statusList.get(0).getSubCourt());
        assertEquals(2, statusList.get(0).getPeriodHour());
        assertEquals(BigDecimal.valueOf(180), statusList.get(0).getAmount());
    }

    @Test
    void should_return_full_court_2_hour_in_night_booking_status_with_amount() {
        List<CourtBookingDto> dtos = new ArrayList<>();
        dtos.add(geneCourtBookingDto("A", null, 19));
        dtos.add(geneCourtBookingDto("A", null, 20));

        List<CourtBookingStatus> statusList = courtBookingService.calPriceByCourtBookingDto(dtos);

        assertEquals(1, statusList.size());
        assertEquals("A", statusList.get(0).getCourt());
        assertNull(statusList.get(0).getSubCourt());
        assertEquals(2, statusList.get(0).getPeriodHour());
        assertEquals(BigDecimal.valueOf(250), statusList.get(0).getAmount());
    }

    @Test
    public void should_create_single_court_booking_after_order_created() {
        //TODO
    }

    @Test
    public void should_create_multiple_court_booking_after_order_created() {
        //TODO
    }
}
