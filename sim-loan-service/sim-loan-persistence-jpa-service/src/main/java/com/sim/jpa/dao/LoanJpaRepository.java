package com.sim.jpa.dao;

import com.sim.jpa.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Repository
@Transactional
public interface LoanJpaRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    <S extends Loan> S save(S entity);


    Optional<Loan> findByUserId(UUID publicId);

}
