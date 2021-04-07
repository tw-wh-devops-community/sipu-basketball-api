package com.pusi.basketball.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class RequestParamValidatorService {
    private static final Logger LOG = LoggerFactory.getLogger(RequestParamValidatorService.class);

    private static final Integer OPEN_TIME = 9;
    private static final Integer CLOSE_TIME = 22;

    public boolean validateGetCourtsRequestParam(String date, Integer startTime, Integer endTime) {
        try {
            LocalDate localDate = LocalDate.parse(date);

            return !localDate.isBefore(LocalDate.now())
                    && startTime >= OPEN_TIME && startTime < CLOSE_TIME
                    && endTime > OPEN_TIME && endTime <= CLOSE_TIME
                    && startTime < endTime;
        } catch (DateTimeParseException e) {
            LOG.info("GetCourts with invalid date param: {}", date);
            return false;
        }
    }
}
