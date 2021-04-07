package com.pusi.basketball.service;

import com.pusi.basketball.model.CourtBooking;
import com.pusi.basketball.repository.CourtBookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CourtBookingService {

    private final CourtBookingRepository courtBookingRepository;

    public CourtBookingService(final CourtBookingRepository courtBookingRepository) {
        this.courtBookingRepository = courtBookingRepository;
    }

    public List<CourtBooking> findCourtBookingRecordOfGivenTimePeriod(
            LocalDate date, Integer startTime, Integer endTime) {
        return courtBookingRepository.findCourtBookingByDateAndHourBetween(date, startTime, endTime);
    }
}
