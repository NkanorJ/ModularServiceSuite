package com.sim.jpa.model;

import com.sim.commons.enumeration.LoanStatus;
import com.sim.commons.enumeration.LoanType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "loan")
public class Loan extends AbstractAuditingEntity implements Serializable {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    private BigDecimal loanAmount;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    private Integer tenure;

    private LocalDate loanStartDate;

    private LocalDate loanEndDate;

    private Date repaymentDate;

    private BigDecimal interestRate;

    private BigDecimal totalAmount;

    private BigDecimal monthlyInstallmentAmount;

    private BigDecimal monthlyInstallment;

    private BigDecimal totalInstallment;

    private BigDecimal paidAmount;

    private BigDecimal outstandingAmount;

    private UUID userId;

    public Loan(UUID id, BigDecimal loanAmount, LoanType loanType, LoanStatus loanStatus, Integer tenure, LocalDate loanStartDate,
                LocalDate loanEndDate, Date repaymentDate, BigDecimal interestRate, BigDecimal totalAmount,
                BigDecimal monthlyInstallmentAmount, BigDecimal monthlyInstallment, BigDecimal totalInstallment,
                BigDecimal paidAmount, BigDecimal outstandingAmount, UUID userId) {
        this.id = id;
        this.loanAmount = loanAmount;
        this.loanType = loanType;
        this.loanStatus = loanStatus;
        this.tenure = tenure;
        this.loanStartDate = loanStartDate;
        this.loanEndDate = loanEndDate;
        this.repaymentDate = repaymentDate;
        this.interestRate = interestRate;
        this.totalAmount = totalAmount;
        this.monthlyInstallmentAmount = monthlyInstallmentAmount;
        this.monthlyInstallment = monthlyInstallment;
        this.totalInstallment = totalInstallment;
        this.paidAmount = paidAmount;
        this.outstandingAmount = outstandingAmount;
        this.userId = userId;
    }

}