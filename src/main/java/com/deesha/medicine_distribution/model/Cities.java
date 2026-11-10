package com.deesha.medicine_distribution.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class Cities {
    @Id
    @Column(name = "cities_id")
    private Integer citiesId;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "postal_code", nullable = false, length = 6)
    private int postalcodes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private Districts district;

    public Integer getCitiesId() {
        return citiesId;
    }

    public void setCitiesId(Integer citiesId) {
        this.citiesId = citiesId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getPostalcodes() {
        return postalcodes;
    }

    public void setPostalcodes(int postalcodes) {
        this.postalcodes = postalcodes;
    }

    public Districts getDistrict() {
        return district;
    }

    public void setDistrict(Districts district) {
        this.district = district;
    }
}