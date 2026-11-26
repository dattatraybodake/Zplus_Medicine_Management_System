package com.deesha.medicine_distribution.dto;

import com.deesha.medicine_distribution.model.Distributor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import com.deesha.medicine_distribution.model.Distributor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public final class DistributorDto {

    private DistributorDto() {}

    public record DistributorRequestDTO(

            @NotBlank
            @Size(max = 255)
            String distributorName,

            @Size(max = 255)
            String tradeName,

            Distributor.BusinessType businessType,

            @NotBlank
            @Size(max = 255)
            String distributorAddress,

            @Size(max = 255)
            String addressLine2,

            @NotBlank
            @Size(max = 80)
            String city,

            @Email
            @NotBlank
            @Size(max = 150)
            String distributorEmail,

            @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid 10-digit Indian mobile number")
            String phone,

            LocalDateTime startDate,

            Distributor.Status status,

            @Size(max = 255)
            String remarks,

            @Size(max = 100)
            String createdBy,

            @Size(max = 100)
            String modifiedBy
    ) {}

    public record DistributorResponseDTO(
            Long distributorId,
            String distributorName,
            String tradeName,
            Distributor.BusinessType businessType,
            String distributorAddress,
            String addressLine2,
            String city,
            String distributorEmail,
            String phone,
            LocalDateTime startDate,
            Distributor.Status status,
            String remarks,
            boolean active,
            boolean eligibleForTrade,
            String createdBy,
            LocalDateTime createdAt,
            String modifiedBy,
            LocalDateTime modifiedAt,
            Long version
    ) {}


}