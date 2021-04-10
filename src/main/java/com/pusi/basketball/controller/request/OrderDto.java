package com.pusi.basketball.controller.request;

import com.sun.istack.NotNull;

import java.time.LocalDate;
import java.util.List;

public class OrderDto {
    @NotNull
    private LocalDate date;

    @NotNull
    private Integer startTime;

    @NotNull
    private Integer endTime;

    @NotNull
    private List<Long> selectedCourts;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public List<Long> getSelectedCourts() {
        return selectedCourts;
    }

    public void setSelectedCourts(List<Long> selectedCourts) {
        this.selectedCourts = selectedCourts;
    }


    @Override
    public String toString() {
        return "OrderDto{" +
                "date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", selectedCourts=" + selectedCourts +
                '}';
    }
}
