package com.sim.jpa.dao;

import com.sim.jpa.model.Loan;
import com.sim.jpa.model.PendingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Repository
@Transactional
public interface PendingRequestJpaRepository extends JpaRepository<PendingRequest, Long>, JpaSpecificationExecutor<PendingRequest> {

    <S extends Loan> S save(S entity);

    Optional<PendingRequest> findByUserId(UUID userId);


}
