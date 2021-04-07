package com.pusi.basketball.controller;

import com.pusi.basketball.controller.response.CourtStatus;
import com.pusi.basketball.controller.response.CourtsStatusResponse;
import com.pusi.basketball.service.CourtService;
import com.pusi.basketball.service.RequestParamValidatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("courts")
public class CourtController {

    private final CourtService courtService;
    private final RequestParamValidatorService validatorService;

    public CourtController(final CourtService courtService, final RequestParamValidatorService validatorService) {
        this.courtService = courtService;
        this.validatorService = validatorService;
    }

    @GetMapping
    public ResponseEntity<CourtsStatusResponse> getCourts(@RequestParam String date, @RequestParam Integer startTime,
                                          @RequestParam Integer endTime) {
        if (!validatorService.validateGetCourtsRequestParam(date, startTime, endTime)) {
            return ResponseEntity.badRequest().body(null);
        }

        List<CourtStatus> courtStatusList = courtService.getCourtsStatus(LocalDate.parse(date), startTime, endTime);

        CourtsStatusResponse courtsStatusResponse = new CourtsStatusResponse();
        courtsStatusResponse.setDate(date);
        courtsStatusResponse.setStartTime(startTime);
        courtsStatusResponse.setEndTime(endTime);
        courtsStatusResponse.setCourts(courtStatusList);
        return ResponseEntity.ok().body(courtsStatusResponse);
    }
}
