package com.pusi.basketball.service.dto;

public class CourtBookingDto {

    private String court;
    private Integer subCourt;
    private Integer bookingHour;

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public Integer getSubCourt() {
        return subCourt;
    }

    public void setSubCourt(Integer subCourt) {
        this.subCourt = subCourt;
    }

    public Integer getBookingHour() {
        return bookingHour;
    }

    public void setBookingHour(Integer bookingHour) {
        this.bookingHour = bookingHour;
    }

    public Boolean isSubCourt() {
        return subCourt != null;
    }
}
