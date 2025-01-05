package com.sim.commons.dto.request;

import com.sim.commons.enumeration.RequestStatus;
import com.sim.commons.enumeration.RequestType;

import java.util.UUID;

public record PendingRequestDto(UUID userId, UUID requestId, RequestType requestType, String additionalInfo,
                                String email, RequestStatus status) {

}