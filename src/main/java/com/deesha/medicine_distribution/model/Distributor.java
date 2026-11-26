package com.deesha.medicine_distribution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "distributor")
@Getter
@Setter
@NoArgsConstructor
public class Distributor {

    public enum BusinessType { PROPRIETORSHIP, PARTNERSHIP, LLP, PRIVATE_LIMITED, PUBLIC_LIMITED }

    public enum Status { ACTIVE, INACTIVE, SUSPENDED, BLACKLISTED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distributor_id")
    private Long distributorId;

    @NotBlank
    @Column(name = "distributor_name", nullable = false, length = 150)
    private String distributorName;

    @Column(name = "trade_name", length = 150)
    private String tradeName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "business_type", nullable = false, length = 30)
    private BusinessType businessType = BusinessType.PRIVATE_LIMITED;

//    @NotBlank
//    @Column(name = "distributor_address", nullable = false, length = 255)
//    private String distributorAddress;
//
//    @Column(name = "address_line2", length = 255)
//    private String addressLine2;
//
//    @NotBlank
//    @Column(name = "city", nullable = false, length = 80)
//    private String city;

    @Email
    @NotBlank
    @Column(name = "distributor_email", nullable = false, length = 150)
    private String distributorEmail;

    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid 10-digit Indian mobile number")
    @Column(name = "phone", length = 10)
    private String phone;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private Status status = Status.ACTIVE;

    @Column(name = "remarks", length = 255)
    private String remarks;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DistributorAddress> address = new ArrayList<>();

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_by", length = 90)
    private String deletedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.startDate == null) {
            this.startDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}