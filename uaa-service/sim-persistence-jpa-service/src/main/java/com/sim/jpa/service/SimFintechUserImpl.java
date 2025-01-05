package com.sim.jpa.service;

import com.sim.commons.dto.registrationdto.SimFintechUserDto;
import com.sim.commons.dto.registrationdto.SecretKey;
import com.sim.core.user.domain.SimUserStorage;
import com.sim.jpa.dao.SimFintechUserJpaRepository;
import com.sim.jpa.model.SimFintechUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimFintechUserImpl implements SimUserStorage {

    private final SimFintechUserJpaRepository simFintechUserJpaRepository;

    @Override
    public Optional<SimFintechUserDto> findByEmail(String email) {
        return simFintechUserJpaRepository.findByEmail(email).map(SimFintechUser::toDto);
    }

    @Override
    public Optional<SimFintechUserDto> findByPhoneNumber(String phoneNumber) {
        return simFintechUserJpaRepository.findByMobile(phoneNumber).map(SimFintechUser::toDto);
    }

    @Override
    public SecretKey createSimUser(SimFintechUserDto simFintechUserDto) {

        SimFintechUser savedUser = simFintechUserJpaRepository.save(new SimFintechUser(simFintechUserDto.id(),
                simFintechUserDto.firstName(), simFintechUserDto.lastName(), null, simFintechUserDto.gender(),
                simFintechUserDto.password(), simFintechUserDto.email(), simFintechUserDto.mobile(),
                simFintechUserDto.secretKey(), simFintechUserDto.publicId(), simFintechUserDto.role()));

        return new SecretKey(savedUser.getEmail(), savedUser.getSecret(), "Client Secret", savedUser.getPublicId());
    }

    @Override
    public Optional<SimFintechUserDto> findUserByPublicId(UUID publicId) {
        return simFintechUserJpaRepository.findSimFintechUserByPublicIdAndDeletedFalse(publicId)
                .map(SimFintechUser::toDto);

    }

    @Override
    public void updateSimUser(SimFintechUserDto simFintechUserDto) {

        simFintechUserJpaRepository.findSimFintechUserByPublicIdAndDeletedFalse(simFintechUserDto.publicId())
                .ifPresent(simFintechUser -> {

                    simFintechUser.setFirstName(simFintechUserDto.firstName());
                    simFintechUser.setLastName(simFintechUserDto.lastName());
                    simFintechUser.setGender(simFintechUserDto.gender());
                    simFintechUser.setDateOfBirth(simFintechUserDto.dateOfBirth());
                    simFintechUser.setRole(simFintechUserDto.role());

                    simFintechUserJpaRepository.save(simFintechUser);
                });
    }

    @Override
    public void deleteSimUser(UUID publicId) {
        simFintechUserJpaRepository.findSimFintechUserByPublicIdAndDeletedFalse(publicId)
                .ifPresent(simFintechUser -> {
                    simFintechUser.setDeleted(true);
                    simFintechUserJpaRepository.save(simFintechUser);
                });

    }
}
