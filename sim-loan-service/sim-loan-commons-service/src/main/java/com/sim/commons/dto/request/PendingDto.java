package com.sim.commons.dto.request;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import com.sim.commons.enumeration.RequestStatus;
import com.sim.commons.enumeration.RequestType;

import java.time.Instant;
import java.util.UUID;

public record PendingDto(RequestType requestType, RequestStatus status, UUID userId, String email, String additionalInfo,
                         Instant createdAt, Instant updatedAt) implements Command<Voidy> {




}