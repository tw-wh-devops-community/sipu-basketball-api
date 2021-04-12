package com.pusi.basketball.service;

import com.pusi.basketball.controller.response.CourtStatus;
import com.pusi.basketball.model.Court;
import com.pusi.basketball.model.CourtBooking;
import com.pusi.basketball.repository.CourtRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourtService {

    private final CourtRepository courtRepository;
    private final CourtBookingService courtBookingService;

    public CourtService(final CourtRepository courtRepository, final CourtBookingService courtBookingService) {
        this.courtRepository = courtRepository;
        this.courtBookingService = courtBookingService;
    }

    public List<CourtStatus> getCourtsStatus(final LocalDate date, final Integer startTime, final Integer endTime) {
        List<Court> courts = (List<Court>) courtRepository.findAll();
        List<CourtBooking> courtBookings = courtBookingService
                .findCourtBookingRecordOfGivenTimePeriod(date, startTime, endTime);

        Set<Long> sets = courtBookings
                .stream()
                .map(courtBooking -> courtBooking.getCourt().getId())
                .collect(Collectors.toSet());

        return courts.stream().map(court -> {
            CourtStatus courtStatus = new CourtStatus();
            courtStatus.setId(court.getId());
            courtStatus.setIsAvailable(!sets.contains(court.getId()));
            courtStatus.setCourt(court.getCourt());
            courtStatus.setSubCourt(court.getSubCourt());

            return courtStatus;
        }).collect(Collectors.toList());
    }

}
