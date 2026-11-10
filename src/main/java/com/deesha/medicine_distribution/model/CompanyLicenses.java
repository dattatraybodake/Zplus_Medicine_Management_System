package com.deesha.medicine_distribution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
        name = "company_license",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_company_license_no",
                columnNames = {"company_id", "license_no"}
        )
)
public class CompanyLicenses {

    public enum LicenseType { MANUFACTURING, DRUG, PAN }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "license_id")
    private Long licenseId;

    // -------- Foreign Key to Company --------
    @NotNull(message = "Company is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private ManufactureCompany company;

    // -------- License Type --------
    @NotNull(message = "License type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "license_type", nullable = false, length = 20)
    private LicenseType licenseType;

    // -------- License Number --------
    @NotBlank(message = "License number is required")
    @Size(max = 50, message = "License number must not exceed 50 characters")
    @Column(name = "license_no", nullable = false, length = 50)
    private String licenseNumber;

    // -------- Dates --------
    @NotNull(message = "Issue date is required")
    @PastOrPresent(message = "Issue date cannot be in the future")
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

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

    // -------- Cross-field validation --------
    @AssertTrue(message = "Expiry date must be after issue date")
    public boolean isDateRangeValid() {
        if (issueDate == null || expiryDate == null) {
            return true;
        }
        return expiryDate.isAfter(issueDate);
    }

    // -------- Lifecycle --------
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}