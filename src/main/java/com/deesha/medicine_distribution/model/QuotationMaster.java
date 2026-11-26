package com.deesha.medicine_distribution.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class QuotationMaster {

    public enum Status {
        Draft,
        Sent,
        Approved,
        Rejected,
        Converted,
        Expired
    }

    private int quotationId;
    private String quotationNumber;
    private LocalDate quotationDate;
    private String userId; // from user from distributor.
    private String distributorCode; //what is code.
    private String distributorName;
    private String distributorEmail;
    private String distributorMobileNumber;

    private String billingAddress; // from distributor address.
    private String shippingAddress;

    private String doctorReference;  // from doctor table,  for phramaciest not distributor.

    private LocalDate validTillDate;
    private BigDecimal totalGrossAmount;
    private BigDecimal totalDiscountAmount;
    private BigDecimal totalNetAmount;
    private BigDecimal totalTaxAmount;
    private BigDecimal roundOffAmount;
    private String currency;
    private String remark;
    private Status status;
    private boolean isActive;
    private boolean isDeleted;
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
    private String deletedAt;
    private LocalDateTime deletedDate;
}