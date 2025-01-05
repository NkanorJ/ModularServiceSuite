package com.sim.commons.dto.response;

import com.sim.commons.enumeration.RequestType;

import java.util.UUID;

public record PendingRequestResponse(UUID userId, UUID requestId, RequestType requestType, String additionalInfo,
                                     String email, String status) {

}