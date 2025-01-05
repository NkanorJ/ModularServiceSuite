package com.sim.core.domain;

import com.sim.commons.dto.request.PendingDto;
import com.sim.commons.dto.request.PendingRequestDto;
import com.sim.commons.enumeration.RequestStatus;

import java.util.Optional;
import java.util.UUID;

public interface PendingRequestStorage {

    Optional<PendingDto> getPendingRequest(UUID userId);

    void createPendingRequest(PendingRequestDto request);

    void updatePendingRequest(UUID uuid, RequestStatus requestStatus);
}
