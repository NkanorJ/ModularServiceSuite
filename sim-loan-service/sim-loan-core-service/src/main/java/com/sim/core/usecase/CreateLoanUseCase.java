package com.sim.core.usecase;

import an.awesome.pipelinr.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sim.commons.dto.request.CreateLoanRequest;
import com.sim.commons.dto.request.PendingRequestDto;
import com.sim.commons.dto.response.CreateLoanResponse;
import com.sim.commons.enumeration.RequestStatus;
import com.sim.commons.enumeration.RequestType;
import com.sim.commons.exception.PendingDualRequestException;
import com.sim.commons.exception.RecordNotFoundException;
import com.sim.commons.messages.MessageSourceService;
import com.sim.core.domain.PendingRequestStorage;
import com.sim.core.port.UserGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class CreateLoanUseCase implements Command.Handler<CreateLoanRequest, CreateLoanResponse>, Command<CreateLoanResponse> {

    private final MessageSourceService messageSourceService;
    private final PendingRequestStorage pendingRequestStorage;

    private final UserGateway userGateway;

    @Override
    public CreateLoanResponse handle(CreateLoanRequest createLoanRequest) {

        var user = userGateway.getUserDetail(createLoanRequest.publicId())
                .orElseThrow(() -> new RecordNotFoundException(messageSourceService.getMessage("user.not.found"), false));

        if (user.loanStatus().equals("ACTIVE"))
            return new CreateLoanResponse(messageSourceService.getMessage("user.has.active.loan"));

        if (user.loanStatus().equals("APPROVED"))
            return new CreateLoanResponse(messageSourceService.getMessage("user.has.approved.loan"));

        String additionalInfoJson = serializeToJson(createLoanRequest);

        createPendingRequest(createLoanRequest, additionalInfoJson);

        return new CreateLoanResponse(messageSourceService.getMessage("loan.request.success"));
    }

    private void createPendingRequest(CreateLoanRequest request, String additionalInfoJson) {

        pendingRequestStorage.getPendingRequest(request.publicId())
                .orElseThrow(() -> new PendingDualRequestException(messageSourceService.getMessage("dual.request"), false));

        pendingRequestStorage.createPendingRequest(new PendingRequestDto(request.publicId(), UUID.randomUUID(),
                RequestType.LOAN_REQUEST, additionalInfoJson, request.email(), RequestStatus.PENDING));

    }

    private String serializeToJson(CreateLoanRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(request);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing settlement mappings to JSON", e);
        }
    }
}
