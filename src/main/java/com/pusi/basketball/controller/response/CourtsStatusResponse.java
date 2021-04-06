package com.pusi.basketball.controller.response;

import java.util.List;

public class CourtsStatusResponse {
    private String date;
    private Integer startTime;
    private Integer endTime;
    private List<CourtStatus> courts;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public List<CourtStatus> getCourts() {
        return courts;
    }

    public void setCourts(List<CourtStatus> courts) {
        this.courts = courts;
    }
}
