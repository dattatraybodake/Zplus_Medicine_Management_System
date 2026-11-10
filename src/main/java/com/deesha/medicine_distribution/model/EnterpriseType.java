package com.deesha.medicine_distribution.model;

import jakarta.persistence.*;

@Entity
@Table(name = "company_enterprise")
public class EnterpriseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enterprise_type_id")
    private Integer enterpriseTypeId;

    @Column(name = "enterprise_type")
    private String enterpriseType;


    @Column(name = "is_active", nullable = false)
    private Boolean active = true;

    public Integer getEnterpriseTypeId() {
        return enterpriseTypeId;
    }

    public void setEnterpriseTypeId(Integer enterpriseTypeId) {
        this.enterpriseTypeId = enterpriseTypeId;
    }

    public String getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}