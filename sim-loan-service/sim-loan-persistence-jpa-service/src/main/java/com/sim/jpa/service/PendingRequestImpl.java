package com.sim.jpa.service;

import com.sim.commons.dto.request.PendingDto;
import com.sim.commons.dto.request.PendingRequestDto;
import com.sim.commons.enumeration.RequestStatus;
import com.sim.core.domain.PendingRequestStorage;
import com.sim.jpa.dao.PendingRequestJpaRepository;
import com.sim.jpa.model.PendingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PendingRequestImpl implements PendingRequestStorage {

    private final PendingRequestJpaRepository pendingRequestJpaRepository;

    @Override
    public Optional<PendingDto> getPendingRequest(UUID userId) {
        return pendingRequestJpaRepository.findByUserId(userId).map(PendingRequest::toDto);
    }

    @Override
    public void createPendingRequest(PendingRequestDto request) {
        pendingRequestJpaRepository.save(new com.sim.jpa.model.PendingRequest(request.requestType(), request.status(),
                request.userId(), request.email(), request.additionalInfo(), Instant.now(), Instant.now())).toDto();

    }

    @Override
    public void updatePendingRequest(UUID uuid, RequestStatus requestStatus) {
        pendingRequestJpaRepository.findByUserId(uuid).ifPresent(pendingRequest -> {
            pendingRequest.setStatus(requestStatus);
            pendingRequest.setUpdatedAt(Instant.now());
            pendingRequestJpaRepository.save(pendingRequest);
        });
    }
}
