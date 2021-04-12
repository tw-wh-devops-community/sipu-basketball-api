package com.pusi.basketball.service;

import com.pusi.basketball.controller.request.OrderDto;
import com.pusi.basketball.controller.response.CourtBookingStatus;
import com.pusi.basketball.model.CourtBooking;
import com.pusi.basketball.repository.CourtBookingRepository;
import com.pusi.basketball.repository.CourtRepository;
import com.pusi.basketball.service.dto.CourtBookingDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourtBookingService {

    private final CourtRepository courtRepository;
    private final CourtBookingRepository courtBookingRepository;

    public CourtBookingService(final CourtBookingRepository courtBookingRepository,
                               final CourtRepository courtRepository) {
        this.courtBookingRepository = courtBookingRepository;
        this.courtRepository = courtRepository;
    }

    public List<CourtBooking> findCourtBookingRecordOfGivenTimePeriod(
            LocalDate date, Integer startTime, Integer endTime) {
        return courtBookingRepository.findCourtBookingByDateAndHourBetween(date, startTime, endTime);
    }

    public List<CourtBookingDto> getCourtBookingsByOrderId(Long orderId) {
        Map<String, CourtBookingDto> bookingDtoMap = new HashMap<>();
        List<CourtBooking> courtBookings = courtBookingRepository.findCourtBookingByOrderId(orderId);
        courtBookings.forEach(courtBooking -> {
            if (!bookingDtoMap.containsKey(generateCourtBookingMappingKey(courtBooking))) {
                CourtBookingDto bookingDto = new CourtBookingDto();
                bookingDto.setCourt(courtBooking.getCourt().getCourt());
                bookingDto.setSubCourt(courtBooking.getCourt().getSubCourt());
                bookingDto.setBookingHour(courtBooking.getHour());
                bookingDtoMap.put(generateCourtBookingMappingKey(courtBooking), bookingDto);
            }
            CourtBookingDto courtBookingDto = bookingDtoMap.get(generateCourtBookingMappingKey(courtBooking));
            if (!courtBookingDto.getSubCourt().equals(courtBooking.getCourt().getSubCourt())) {
                courtBookingDto.setSubCourt(null);
            }
        });
        return new ArrayList<>(bookingDtoMap.values());
    }

    public void createCourtBookings(Long orderId, OrderDto orderDto) {
        List<CourtBooking> courtBookingsToSave = new ArrayList<>();
        for (int hour = orderDto.getStartTime(); hour < orderDto.getEndTime(); hour++) {
            int finalHour = hour;
            orderDto.getSelectedCourts().forEach(courtId -> {
                CourtBooking courtBooking = new CourtBooking();
                courtBooking.setDate(orderDto.getDate());
                courtBooking.setHour(finalHour);
                courtBooking.setOrderId(orderId);
                courtBooking.setCourt(courtRepository.findById(courtId).orElseThrow());

                courtBookingsToSave.add(courtBooking);
            });
        }

        if (!courtBookingsToSave.isEmpty()) {
            courtBookingRepository.saveAll(courtBookingsToSave);
        }
    }

    public List<CourtBookingStatus> calPriceByCourtBookingDto(List<CourtBookingDto> bookingDtoList) {
        Map<String, CourtBookingStatus> courtBookingStatusMap = new HashMap<>();
        bookingDtoList.forEach(bookingDto -> {
            if (!courtBookingStatusMap.containsKey(getCourtMappingKey(bookingDto))) {
                CourtBookingStatus status = new CourtBookingStatus();
                status.setCourt(bookingDto.getCourt());
                status.setSubCourt(bookingDto.getSubCourt());
                status.setAmount(BigDecimal.ZERO);
                status.setPeriodHour(0);
                courtBookingStatusMap.put(getCourtMappingKey(bookingDto), status);
            }
            CourtBookingStatus status = courtBookingStatusMap.get(getCourtMappingKey(bookingDto));
            status.setPeriodHour(status.getPeriodHour() + 1);
            status.setAmount(
                    status.getAmount().add(
                            CourtPriceService.calPriceByHourAndIsSubCourt(
                                    bookingDto.getBookingHour(), bookingDto.isSubCourt()
                            )));
        });
        return new ArrayList<>(courtBookingStatusMap.values());
    }

    private static String getCourtMappingKey(CourtBookingDto bookingDto) {
        return String.format("%s-%s", bookingDto.getCourt(), bookingDto.getSubCourt());
    }

    private static String generateCourtBookingMappingKey(CourtBooking courtBooking) {
        return String.format("%d-%s", courtBooking.getHour(), courtBooking.getCourt().getCourt());
    }
}
