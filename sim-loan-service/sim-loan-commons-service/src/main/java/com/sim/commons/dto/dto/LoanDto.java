package com.sim.commons.dto.dto;

import com.sim.commons.enumeration.LoanStatus;
import com.sim.commons.enumeration.LoanType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public record LoanDto(BigDecimal loanAmount, LoanType loanType, LoanStatus loanStatus, Integer tenure,
                      LocalDate loanStartDate,
                      LocalDate loanEndDate, Date repaymentDate, BigDecimal interestRate, BigDecimal totalAmount,
                      BigDecimal monthlyInstallmentAmount, BigDecimal monthlyInstallment, BigDecimal totalInstallment,
                      BigDecimal paidAmount, BigDecimal outstandingAmount, UUID userId) {


}