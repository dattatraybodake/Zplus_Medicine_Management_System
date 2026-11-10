package com.deesha.medicine_distribution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "company", uniqueConstraints = @UniqueConstraint(name = "uk_company_name", columnNames = {"company_name"}))
@ToString
public class ManufactureCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    // -------- Foreign Key: Enterprise Type --------
    @NotNull(message = "Enterprise type is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enterprise_type_id", nullable = false)
    private EnterpriseType enterpriseType;

    // -------- Core fields with validation --------
    @NotBlank(message = "Company name is required")
    @Size(max = 150, message = "Company name must not exceed 150 characters")
    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @Size(max = 150)
    @Column(name = "trade_name", length = 150)
    private String tradeName;

    @Size(max = 255)
    @Column(name = "industry", length = 255)
    private String industry;

    @Size(max = 1000)
    @Column(name = "products", length = 1000)
    private String products;

    @Size(max = 150)
    @Pattern(
            regexp = "^(https?://)?([\\w.-]+)\\.([a-z]{2,})(/\\S*)?$",
            message = "Invalid website URL",
            flags = Pattern.Flag.CASE_INSENSITIVE
    )
    @Column(name = "website", length = 150)
    private String website;

    // -------- Dates --------
    @PastOrPresent(message = "Company start date cannot be in the future")
    @Column(name = "company_start_date")
    private LocalDate companyStartDate;

    @Column(name = "company_end_date")
    private LocalDate companyEndDate;

    // -------- Status --------
    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    // -------- Joins: one company -> many addresses --------
    @ToString.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyAddress> addresses = new ArrayList<>();

    // -------- Joins: one company -> many bank accounts --------
    @ToString.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyBankAccount> bankAccounts = new ArrayList<>();

    // -------- Joins: one company -> many licenses --------
    @ToString.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyLicenses> licenses = new ArrayList<>();   // ✅ matches entity name

    // -------- Joins: one company -> many people --------
    @ToString.Exclude
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyPerson> people = new ArrayList<>();

    // -------- Joins: one company -> many medicines (if medicines belong to a company) --------
    // Uncomment if your Medicine entity has a `company` FK
    // @ToString.Exclude
    // @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Medicine> medicines = new ArrayList<>();

    // -------- Audit --------
    @Size(max = 100)
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Size(max = 100)
    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Size(max = 90)
    @Column(name = "deleted_by", length = 90)
    private String deletedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // -------- Lifecycle --------
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (companyEndDate != null && companyEndDate.isBefore(LocalDate.now())) {
            active = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
        if (companyEndDate != null && companyEndDate.isBefore(LocalDate.now())) {
            active = false;
        }
    }

    @AssertTrue(message = "Company end date must be after start date")
    public boolean isDateRangeValid() {
        if (companyStartDate == null || companyEndDate == null) {
            return true;
        }
        return companyEndDate.isAfter(companyStartDate);
    }
}