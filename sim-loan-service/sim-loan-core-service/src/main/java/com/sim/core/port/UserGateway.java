package com.sim.core.port;

import com.sim.commons.dto.response.GetUsersResponse;

import java.util.Optional;
import java.util.UUID;


public interface UserGateway {

    Optional<GetUsersResponse> getUserDetail(UUID publicId);
}
