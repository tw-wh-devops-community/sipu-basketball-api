package com.pusi.basketball.service;

import com.pusi.basketball.controller.response.CourtStatus;
import com.pusi.basketball.repository.CourtRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class CourtService {

    private final CourtRepository courtRepository;
    private final CourtBookingService courtBookingService;

    public CourtService(final CourtRepository courtRepository, final CourtBookingService courtBookingService) {
        this.courtRepository = courtRepository;
        this.courtBookingService = courtBookingService;
    }

    public List<CourtStatus> getCourtsStatus(final LocalDate date, final Integer startTime, final Integer endTime) {
        //TODO response court status base on existing court booking records
        return Collections.emptyList();
    }

}
