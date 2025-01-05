package com.sim.core.usecase;

import an.awesome.pipelinr.Command;
import com.sim.commons.dto.request.GetLoanRequest;
import com.sim.commons.dto.response.GetLoanResponse;
import com.sim.commons.exception.LoanNotFoundException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.domain.LoanStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetLoanUseCase implements Command.Handler<GetLoanRequest, GetLoanResponse>, Command<GetLoanResponse> {

    private final MessageSourceService messageSourceService;

    private final LoanStorage loanStorage;

    @Override
    public GetLoanResponse handle(GetLoanRequest getLoanRequest) {

        var response = loanStorage.getLoan(getLoanRequest.publicId())
                .orElseThrow(() -> new LoanNotFoundException(messageSourceService.getMessage("loan.not.found"), false));

        return new GetLoanResponse(response.loanAmount(), response.loanType(), response.loanStatus(), response.tenure(),
                response.loanStartDate(), response.loanEndDate(), response.repaymentDate(), response.interestRate(),
                response.totalAmount(), response.monthlyInstallmentAmount(), response.monthlyInstallment(),
                response.totalInstallment(), response.paidAmount(), response.outstandingAmount(), response.userId());

    }
}
