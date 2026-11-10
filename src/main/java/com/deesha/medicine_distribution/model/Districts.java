package com.deesha.medicine_distribution.model;

import jakarta.persistence.*;

@Entity
@Table(name = "districts")
public class Districts {
    @Id
    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "district_name")
    private String districtName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private States state;

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }
}