package com.sim.jpa.service;

import com.sim.auth.service.port.SimFintechUserRepository;
import com.sim.commons.dto.registrationdto.SimFintechUserDto;
import com.sim.jpa.dao.SimFintechUserJpaRepository;
import com.sim.jpa.model.SimFintechUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class SimFintechUserStorage implements SimFintechUserRepository {

    private final SimFintechUserJpaRepository simFintechUserJpaRepository;

    @Override
    public Optional<SimFintechUserDto> findUserByEmail(String email) {
        return simFintechUserJpaRepository.findByEmail(email).map(SimFintechUser::toDto);
    }
    
}
