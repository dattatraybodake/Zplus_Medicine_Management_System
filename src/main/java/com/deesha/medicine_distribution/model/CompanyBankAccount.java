package com.deesha.medicine_distribution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "company_banks")
public class CompanyBankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private Long bankAccountId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private ManufactureCompany company;

    @Column(name = "bank_name", nullable = false, length = 90)
    private String bankName;

    @Column(name = "bank_account_name", nullable = false, length = 90)
    private String accountName;

    @Column(name = "bank_account_number", unique = true, nullable = false, length = 18)
    @Pattern(regexp = "^[0-9]{9,18}$")
    private String accountNumber;

    // Not unique: many accounts can share the same IFSC (same branch)
    @Column(name = "ifsc_code", nullable = false, length = 11)
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$")
    private String ifscCode;

    @Column(name = "branch_name", length = 90)
    private String branchName;

    @Column(name = "active", nullable = false)
    private Boolean active = true;
}