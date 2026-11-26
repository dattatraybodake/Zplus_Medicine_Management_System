package com.deesha.medicine_distribution.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "quotation_details")
@Getter
@Setter
public class QuotationDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quotation_details_id")
    private Long quotationDetailsId;

    // from quotation master - stored as FK id
    @Column(name = "quotation_id", nullable = false)
    private Long quotationId;

    // from medicine table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Column(name = "medicine_name")
    private String medicineName;

    @Column(name = "precise_generic_name")
    private String preciseGenericName;

    // from manufacturer_company
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private ManufactureCompany company;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "batch_number")
    private String batchNumber;

    @Column(name = "hsn_code")
    private String hsnCode;

    @Column(name = "packing_type")
    private String packingType;

    @Column(name = "pack_size")
    private String packSize;

    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

    @Column(name = "quantity", precision = 18, scale = 4)
    private BigDecimal quantity;

    @Column(name = "free_quantity", precision = 18, scale = 4)
    private BigDecimal freeQuantity;

    @Column(name = "mrp", precision = 18, scale = 2)
    private BigDecimal mrp;

    @Column(name = "rate", precision = 18, scale = 2)
    private BigDecimal rate;

    @Column(name = "discount_percentage", precision = 8, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "discount_amount", precision = 18, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "scheme_type")
    private String schemeType;

    @Column(name = "scheme_value")
    private String schemeValue;

    @Column(name = "gross_amount", precision = 18, scale = 2)
    private BigDecimal grossAmount;

    @Column(name = "taxable_amount", precision = 18, scale = 2)
    private BigDecimal taxableAmount;

    @Column(name = "gst_percentage", precision = 8, scale = 2)
    private BigDecimal gstPercentage;

    @Column(name = "tax_amount", precision = 18, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "net_amount", precision = 18, scale = 2)
    private BigDecimal netAmount;

    @Column(name = "remark", length = 500)
    private String remark;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    // ---------- Lifecycle hooks ----------

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        if (!this.isActive) {
            this.isActive = true;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }
}