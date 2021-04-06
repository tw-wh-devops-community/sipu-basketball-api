package com.pusi.basketball.controller;

import com.pusi.basketball.controller.response.CourtStatus;
import com.pusi.basketball.controller.response.CourtsStatusResponse;
import com.pusi.basketball.service.CourtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("courts")
public class CourtController {

    private CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @GetMapping
    public ResponseEntity<CourtsStatusResponse> getCourts(@RequestParam String date, @RequestParam Integer startTime,
                                          @RequestParam Integer endTime) {
        List<CourtStatus> courtStatusList = courtService.getCourtsStatus(date, startTime, endTime);

        CourtsStatusResponse courtsStatusResponse = new CourtsStatusResponse();
        courtsStatusResponse.setDate(date);
        courtsStatusResponse.setStartTime(startTime);
        courtsStatusResponse.setEndTime(endTime);
        courtsStatusResponse.setCourts(courtStatusList);
        return ResponseEntity.ok().body(courtsStatusResponse);
    }
}
