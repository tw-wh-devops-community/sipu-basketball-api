package com.pusi.basketball.controller.response;

public class CourtStatus {
    private Long id;
    private String court;
    private Integer subCourt;
    private Boolean isAvailable;

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

    public Long getId() {
        return id;
    }

    public void setId(Long courtId) {
        this.id = courtId;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }
}
