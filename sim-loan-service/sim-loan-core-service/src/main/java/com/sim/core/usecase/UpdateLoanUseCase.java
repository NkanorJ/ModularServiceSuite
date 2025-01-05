package com.sim.core.usecase;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.sim.commons.dto.request.UpdateLoanRequest;
import com.sim.commons.exception.LoanNotFoundException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.domain.LoanStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class UpdateLoanUseCase implements Command.Handler<UpdateLoanRequest, Voidy>, Command<Voidy> {

    private final MessageSourceService messageSourceService;

    private final LoanStorage loanStorage;

    @Override
    public Voidy handle(UpdateLoanRequest updateLoanRequest) {
        loanStorage.getLoan(updateLoanRequest.userId())
                .orElseThrow(() -> new LoanNotFoundException(messageSourceService.getMessage("loan.not.found"), false));

        loanStorage.updateLoanStatus(updateLoanRequest.userId(), updateLoanRequest.status());

        return new Voidy();
    }
}
