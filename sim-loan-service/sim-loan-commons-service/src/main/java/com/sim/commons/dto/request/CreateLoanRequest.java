package com.sim.commons.dto.request;

import com.sim.commons.dto.response.CreateLoanResponse;
import com.sim.commons.enumeration.LoanType;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateLoanRequest(UUID publicId, LoanType loanType, BigDecimal loanAmount, String loanDuration, String email)
        implements an.awesome.pipelinr.Command<CreateLoanResponse> {

}
