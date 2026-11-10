package com.deesha.medicine_distribution.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Long medicineId;

    @Column(name = "medicine_name", nullable = false, length = 150)
    private String medicineName;

    @Column(name = "brand_name", length = 150)
    private String brandName;

    @Column(name = "medicine_type", length = 100)
    private String medicineType;

    @Column(name = "precise_generic_name", length = 150)
    private String preciseGenericName;

    @Column(name = "commercial_brand_name", length = 150)
    private String commercialBrandName;

    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "unit_of_measure", length = 50)
    private String unitOfMeasure;

    @Column(name = "tax_category", length = 50)
    private String taxCategory;

    @Column(name = "gst_percent", precision = 5, scale = 2)
    private BigDecimal gstPercent;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    // -------- Foreign Keys --------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_category_id")
    private MedicineCategories medicineCategory; // If you have a MedicineType entity, replace with @ManyToOne
}
