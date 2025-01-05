package com.sim.core.user.domain;

import com.sim.commons.dto.registrationdto.SimFintechUserDto;
import com.sim.commons.dto.registrationdto.SecretKey;

import java.util.Optional;
import java.util.UUID;

public interface SimUserStorage {

    Optional<SimFintechUserDto> findByEmail(String email);

    Optional<SimFintechUserDto> findByPhoneNumber(String phoneNumber);

    SecretKey createSimUser(SimFintechUserDto simFintechUserDto);

    Optional<SimFintechUserDto> findUserByPublicId(UUID publicId);

    void updateSimUser(SimFintechUserDto simFintechUserDto);

    void deleteSimUser(UUID publicId);

}
