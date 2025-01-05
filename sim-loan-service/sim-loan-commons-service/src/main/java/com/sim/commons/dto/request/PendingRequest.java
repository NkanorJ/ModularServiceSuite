package com.sim.commons.dto.request;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.sim.commons.enumeration.RequestType;

import java.util.UUID;

public record PendingRequest(UUID userId, UUID requestId, RequestType requestType, String additionalInfo,
                             String email, String status) implements Command<Voidy> {

}