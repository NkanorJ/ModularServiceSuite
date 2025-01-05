package com.sim.core.domain;

import com.sim.commons.dto.dto.LoanDto;
import com.sim.commons.dto.request.CreateLoanRequest;

import java.util.Optional;
import java.util.UUID;

public interface LoanStorage {

    void createLoan(CreateLoanRequest createLoanRequest);

    Optional<LoanDto> getLoan(UUID uuid);

    void updateLoanStatus(UUID uuid, String status);
}
