package com.sim.auth.service.port;

import com.sim.commons.dto.registrationdto.SimFintechUserDto;

import java.util.Optional;

public interface SimFintechUserRepository {

    Optional<SimFintechUserDto> findUserByEmail(String email);

}
