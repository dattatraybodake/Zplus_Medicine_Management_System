package com.deesha.medicine_distribution.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class QuotationDto {

    public QuotationDto() {
    }

    // ============================================================
    // REQUEST
    // ============================================================
    public record QuatationDetailsRequest(

            Integer quotationId,

            @NotNull(message = "medicineId is required")
            @Positive(message = "medicineId must be positive")
            Long medicineId,

            @NotBlank(message = "medicineName is required")
            @Size(max = 255)
            String medicineName,

            @Size(max = 255)
            String preciseGenericName,

            @Positive(message = "companyId must be positive")
            Long companyId,

            @Size(max = 255)
            String companyName,

            @Size(max = 100)
            String batchNumber,

            @Size(max = 20)
            String hsnCode,

            @Size(max = 50)
            String packingType,

            @Size(max = 50)
            String packSize,

            @Size(max = 20)
            String unitOfMeasure,

            @NotNull(message = "quantity is required")
            @DecimalMin(value = "0.0001", message = "quantity must be greater than 0")
            @Digits(integer = 14, fraction = 4)
            BigDecimal quantity,

            @DecimalMin(value = "0.0")
            @Digits(integer = 14, fraction = 4)
            BigDecimal freeQuantity,

            @NotNull(message = "mrp is required")
            @DecimalMin(value = "0.0")
            @Digits(integer = 16, fraction = 2)
            BigDecimal mrp,

            @NotNull(message = "rate is required")
            @DecimalMin(value = "0.0")
            @Digits(integer = 16, fraction = 2)
            BigDecimal rate,

            @DecimalMin(value = "0.0")
            @DecimalMax(value = "100.0")
            @Digits(integer = 6, fraction = 2)
            BigDecimal discountPercentage,

            @DecimalMin(value = "0.0")
            @Digits(integer = 16, fraction = 2)
            BigDecimal discountAmount,

            @Size(max = 30)
            String schemeType,

            @Size(max = 100)
            String schemeValue,

            @Digits(integer = 16, fraction = 2)
            BigDecimal grossAmount,

            @Digits(integer = 16, fraction = 2)
            BigDecimal taxableAmount,

            @DecimalMin(value = "0.0")
            @DecimalMax(value = "100.0")
            @Digits(integer = 6, fraction = 2)
            BigDecimal gstPercentage,

            @Digits(integer = 16, fraction = 2)
            BigDecimal taxAmount,

            @Digits(integer = 16, fraction = 2)
            BigDecimal netAmount,

            @Size(max = 500)
            String remark
    ) {
    }

    // ============================================================
    // RESPONSE
    // ============================================================
    public record QuatationDetailsResponse(

            Long quotationDetailsId,
            Long quotationId,

            MedicineSummary medicine,
            ManufactureCompanySummary company,

            String medicineName,
            String preciseGenericName,
            String companyName,
            String batchNumber,
            String hsnCode,

            String packingType,
            String packSize,
            String unitOfMeasure,
            BigDecimal quantity,
            BigDecimal freeQuantity,

            BigDecimal mrp,
            BigDecimal rate,
            BigDecimal discountPercentage,
            BigDecimal discountAmount,

            String schemeType,
            String schemeValue,

            BigDecimal grossAmount,
            BigDecimal taxableAmount,
            BigDecimal gstPercentage,
            BigDecimal taxAmount,
            BigDecimal netAmount,

            String remark,
            boolean isActive,

            String createdBy,
            LocalDateTime createdDate,
            String modifiedBy,
            LocalDateTime modifiedDate
    ) {

        // ---------- Nested summary records ----------

        public record MedicineSummary(
                Long medicineId,
                String medicineName,
                String preciseGenericName
        ) {
        }

        public record ManufactureCompanySummary(
                Long companyId,
                String companyName
        ) {
        }
    }
}
