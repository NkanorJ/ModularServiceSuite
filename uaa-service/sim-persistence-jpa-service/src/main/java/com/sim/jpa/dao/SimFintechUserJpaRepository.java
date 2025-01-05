package com.sim.jpa.dao;

import com.sim.jpa.model.SimFintechUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Repository
@Transactional
public interface SimFintechUserJpaRepository extends JpaRepository<SimFintechUser, Long>, JpaSpecificationExecutor<SimFintechUser> {

    <S extends SimFintechUser> S save(S entity);

    Optional<SimFintechUser> findByEmail(String email);

    Optional<SimFintechUser> findByMobile(String phoneNumber);

    Optional<SimFintechUser> findSimFintechUserByPublicIdAndDeletedFalse(UUID publicId);

}
