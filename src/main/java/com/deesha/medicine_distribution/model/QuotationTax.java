package com.deesha.medicine_distribution.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuotationTax {

    private int QuotationTaxId;
    private int quotationId;
    private String taxType;
    private BigDecimal taxRate;
    private BigDecimal taxableAmount;
    private BigDecimal cgstPercentage;
    private BigDecimal cgstAmount;
    private BigDecimal sgstPercentage;
    private BigDecimal sgstAmount;
    private BigDecimal igstRate;
    private BigDecimal igstAmount;
    private BigDecimal cessRate;
    private BigDecimal cessAmount;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String modifiedBy;
    private String deletedBy;
    private LocalDateTime deletedDate;
}