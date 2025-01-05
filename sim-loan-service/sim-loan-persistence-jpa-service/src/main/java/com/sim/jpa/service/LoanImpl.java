package com.sim.jpa.service;

import com.sim.commons.dto.dto.LoanDto;
import com.sim.commons.dto.request.CreateLoanRequest;
import com.sim.commons.enumeration.LoanStatus;
import com.sim.core.domain.LoanStorage;
import com.sim.jpa.dao.LoanJpaRepository;
import com.sim.jpa.model.Loan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoanImpl implements LoanStorage {

    private final LoanJpaRepository loanJpaRepository;

    @Override
    public void createLoan(CreateLoanRequest request) {
        loanJpaRepository.save(new Loan(UUID.randomUUID(), request.loanAmount(), request.loanType(), LoanStatus.APPROVED,
                0, null, null, null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, request.publicId()));

    }

    @Override
    public Optional<LoanDto> getLoan(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void updateLoanStatus(UUID uuid, String status) {
        loanJpaRepository.findByUserId(uuid).ifPresent(loan -> {
            loan.setLoanStatus(LoanStatus.valueOf(status));
            loanJpaRepository.save(loan);
        });
    }


}
