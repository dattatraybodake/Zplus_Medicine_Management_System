package com.deesha.medicine_distribution.dto;

import com.deesha.medicine_distribution.model.CompanyAddress.AddressType;
import com.deesha.medicine_distribution.model.CompanyLicenses;
import jakarta.validation.constraints.*;
import com.deesha.medicine_distribution.model.ManufactureCompany;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

    public final class CompanyDto {

        private CompanyDto() {}

//         ---------- Enterprise type ----------
        public record EnterpriseTypeRequest(
                @NotBlank
                @Size(max = 255)
                String enterpriseType,

                Boolean isActive
        ) {}

        public record EnterpriseTypeResponse(
                Integer enterpriseTypeId,
                String enterpriseType,
                Boolean isActive
        ) {}

        public record CompanyRequest(

                @NotNull(message = "Enterprise type is required")
                Integer enterpriseTypeId,

                // companyId is NOT in request (server-generated on create; path param on update)
                @NotBlank(message = "Company name is required")
                @Size(max = 150)
                String companyName,

                @Size(max = 150)
                String tradeName,

                @Size(max = 255)
                String industry,

                @Size(max = 1000)
                String products,

                @Size(max = 150)
                @Pattern(
                        regexp = "^(https?://)?([\\w.-]+)\\.([a-z]{2,})(/\\S*)?$",
                        message = "Invalid website URL",
                        flags = Pattern.Flag.CASE_INSENSITIVE
                )
                String website,

                Boolean active,

                @PastOrPresent(message = "Company start date cannot be in the future")
                LocalDate companyStartDate,

                LocalDate companyEndDate,

                @Size(max = 100)
                String createdBy,

                @Size(max = 100)
                String modifiedBy
        ) {}

        public record CompanyResponse(
                Long companyId,
                Integer enterpriseTypeId,
                String enterpriseTypeName,     // ← helpful for UI
                String companyName,
                String tradeName,
                String industry,
                String products,
                String website,
                boolean active,
                LocalDate companyStartDate,
                LocalDate companyEndDate,
                String createdBy,
                LocalDateTime createdAt,
                String modifiedBy,
                LocalDateTime modifiedAt,
                String deletedBy,
                LocalDateTime deletedAt
        ) {}

        // ---------- Address ----------
        public record AddressRequest(

                @NotNull
                AddressType addressType,

                @NotBlank
                @Size(max = 255)
                String addressLine1,

                @Size(max = 255)
                String addressLine2,

                @NotNull
                Integer citiesId
        ) {}

        public record AddressResponse(
                Long addressId,
                Long companyId,
                String companyName, AddressType addressType,
                String addressLine1,
                String addressLine2,
                String city,
                String district,
                String state,
                String country,
                Integer postalcodes
        ) {}

        // ---------- Bank account ----------
        public record BankAccountRequest(
                Long bankAccountId,

                @NotBlank
                @Size(max = 90)
                String bankName,

                @NotBlank
                @Size(max = 90)
                String accountName,

                @NotBlank
                @Pattern(regexp = "^[0-9]{9,18}$", message = "Account number must be 9-18 digits")
                String accountNumber,

                @NotBlank
                @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC")
                String ifscCode,

                @Size(max = 90)
                String branchName,

                Boolean isActive
        ) {}

        public record BankAccountResponse(
                Long bankAccountId,
                ManufactureCompany companyId,
                String bankName,
                String accountName,
                String accountNumber,
                String ifscCode,
                String branchName,
                Boolean isActive
        ) {}

        // ---------- License ----------
        public record LicenseRequest(

                @NotNull(message = "License type is required")
                CompanyLicenses.LicenseType licenseType,

                @NotBlank(message = "License number is required")
                @Size(max = 50)
                String licenseNumber,

                @NotNull(message = "Issue date is required")
                @PastOrPresent(message = "Issue date cannot be in the future")
                LocalDate issueDate,

                @NotNull(message = "Expiry date is required")
                @Future(message = "Expiry date must be in the future")
                LocalDate expiryDate,

                @Size(max = 100)
                String createdBy
        ) {}

        public record LicenseResponse(
                Long licenseId,
                Long companyId,
                String companyName,
                CompanyLicenses.LicenseType licenseType,
                String licenseNumber,
                LocalDate issueDate,
                LocalDate expiryDate,
                String createdBy,
                LocalDateTime createdAt,
                String modifiedBy,
                LocalDateTime modifiedAt
        ) {}
            // ---------- Medicine category ----------
        public record CategoryRequest(
                    @NotBlank
                    @Size(max = 255)
                    String categoryName
            ) {}

        public record CategoryResponse(
                    Integer categoryId,
                    String categoryName
            ) {}

        // ---------- Medicine ----------
        public record MedicineRequest(
                @NotBlank
                @Size(max = 255)
                String medicineName,

                @Size(max = 255)
                String brandName,

                @Size(max = 100)
                String medicineType,

                @Size(max = 255)
                String preciseGenericName,

                @Size(max = 255)
                String commercialBrandName,

                @NotNull
                @DecimalMin(value = "0.0", inclusive = false)
                @Digits(integer = 10, fraction = 2)
                BigDecimal price,

                @Size(max = 50)
                String unitOfMeasure,

                @Size(max = 100)
                String taxCategory,

                @DecimalMin("0.0")
                @DecimalMax("100.0")
                @Digits(integer = 3, fraction = 2)
                BigDecimal gstPercent,

                @Min(0)
                Integer totalQuantity,

                Integer typesId,

                @NotNull
                Integer medicineCategoryId,

                @Size(max = 100)
                String createdBy,

                @Size(max = 100)
                String modifiedBy
        ) {}

        public record MedicineResponse(
                Long medicineId,
                String medicineName,
                String brandName,
                String medicineType,
                String preciseGenericName,
                String commercialBrandName,
                BigDecimal price,
                String unitOfMeasure,
                String taxCategory,
                BigDecimal gstPercent,
                Integer totalQuantity,
                Integer typesId,
                Integer medicineCategoryId,
                String medicineCategoryName,
                String createdBy,
                LocalDateTime createdAt,
                String modifiedBy,
                LocalDateTime modifiedAt,
                Boolean isDeleted
        ) {}

        // ---------- Request ----------
        public record DepartmentRequest(

                @NotBlank(message = "CompanyDepartment name is required")
                @Size(max = 150)
                String departmentName,

                @Size(max = 50)
                String departmentCode,

                @Size(max = 500)
                String description,

                @Size(max = 150)
                String headOfDepartment,

                Boolean isActive,

                @Size(max = 100)
                String createdBy
        ) {}

        // ---------- Response ----------
        public record DepartmentResponse(
                Long departmentId,
                String departmentName,
                String departmentCode,
                String description,
                String headOfDepartment,
                Boolean isActive,
                String createdBy,
                LocalDateTime createdAt,
                String modifiedBy,
                LocalDateTime modifiedAt
        ) {}

        // ---------- Request ----------
        public record CompanyPersonRequest(

                @NotBlank(message = "Full name is required")
                @Size(max = 150)
                String fullName,

                @Email(message = "Invalid email format")
                @Size(max = 150)
                String email,

                @Pattern(regexp = "^[0-9+\\-\\s]{7,20}$", message = "Phone number must be 7-20 digits")
                String phoneNumber,

                @Size(max = 100)
                String designation,

                LocalDate dateOfJoining,

                @Size(max = 500)
                String address,

                Boolean isActive,

                Long departmentId,

                @Size(max = 100)
                String createdBy
        ) {}

        // ---------- Response ----------
        public record CompanyPersonResponse(
                Long personId,
                String fullName,
                String email,
                String phoneNumber,
                String designation,
                LocalDate dateOfJoining,
                String address,
                Boolean isActive,
                Long departmentId,
                String departmentName,
                String createdBy,
                LocalDateTime createdAt,
                String modifiedBy,
                LocalDateTime modifiedAt
        ) {}
    }