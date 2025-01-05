package com.sim.jpa.model;

import com.sim.commons.dto.request.PendingDto;
import com.sim.commons.enumeration.RequestStatus;
import com.sim.commons.enumeration.RequestType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "requests")
public class PendingRequest extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RequestType requestType;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    private UUID userId;
    private String email;
    private String additionalInfo;
    private Instant createdAt;
    private Instant updatedAt;

    public PendingRequest(RequestType requestType, RequestStatus status, UUID userId, String email, String additionalInfo, Instant createdAt, Instant updatedAt) {
        this.requestType = requestType;
        this.status = status;
        this.userId = userId;
        this.email = email;
        this.additionalInfo = additionalInfo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PendingDto toDto() {
        return new PendingDto(requestType, status, userId, email, additionalInfo, createdAt, updatedAt);
    }

}