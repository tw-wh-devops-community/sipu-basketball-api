package com.pusi.basketball.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Court {

    private Long id;
    private String court;
    private Integer subCourt;

    @Id
    public Long getId() {
        return id;
    }

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

    public void setId(Long id) {
        this.id = id;
    }



}
