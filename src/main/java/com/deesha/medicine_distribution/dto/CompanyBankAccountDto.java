package com.deesha.medicine_distribution.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyBankAccountDto {

    private String bankName;
    private String accountName;
    private String accountNumber;
    private String ifscCode;
    private String branchName;
    private Boolean isActive;
}
