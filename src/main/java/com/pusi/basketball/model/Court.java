package com.pusi.basketball.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "courts")
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String court;
    private Integer subCourt;

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
