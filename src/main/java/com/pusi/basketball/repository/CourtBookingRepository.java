package com.pusi.basketball.repository;

import com.pusi.basketball.model.CourtBooking;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CourtBookingRepository extends CrudRepository<CourtBooking, Long> {

    List<CourtBooking> findCourtBookingByDateAndHourBetween(LocalDate date, Integer startTime, Integer endTime);
}
