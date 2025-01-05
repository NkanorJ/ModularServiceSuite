package com.sim.core.usecase;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sim.commons.dto.request.CreateLoanRequest;
import com.sim.commons.dto.request.PendingRequest;
import com.sim.commons.enumeration.RequestStatus;
import com.sim.commons.exception.PendingDualRequestException;
import com.sim.commons.exception.RecordNotFoundException;
import com.sim.commons.exception.UserRoleException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.domain.LoanStorage;
import com.sim.core.domain.PendingRequestStorage;
import com.sim.core.port.UserGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HandlePendingRequestUsecase implements Command.Handler<PendingRequest, Voidy>, Command<Voidy> {

    private final MessageSourceService messageSourceService;
    private final PendingRequestStorage pendingRequestStorage;
    private final LoanStorage loanStorage;
    private final UserGateway userGateway;
    private final ObjectMapper objectMapper;

    @Override
    public Voidy handle(PendingRequest request) {

        var pendingRequest = pendingRequestStorage.getPendingRequest(request.userId())
                .orElseThrow(() -> new PendingDualRequestException(messageSourceService.getMessage("dual.request"), false));

        var user = userGateway.getUserDetail(request.userId())
                .orElseThrow(() -> new RecordNotFoundException(messageSourceService.getMessage("user.not.found"), false));

        if (user.role().equals("USER"))
            throw new UserRoleException(messageSourceService.getMessage("user.is.not.admin"), false);

        if (!RequestStatus.PENDING.equals(pendingRequest.status()))
            throw new PendingDualRequestException(messageSourceService.getMessage("request.treated"), false);

        if (RequestStatus.APPROVED.equals(RequestStatus.valueOf(request.status()))) {

            try {
                var userLoanRequest = objectMapper.readValue(pendingRequest.additionalInfo(), CreateLoanRequest.class);

                loanStorage.createLoan(userLoanRequest);

                pendingRequestStorage.updatePendingRequest(request.userId(), RequestStatus.valueOf(request.status()));

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
        return new Voidy();
    }
}