package com.sim.gateway.user;

import com.sim.commons.dto.response.GetUsersResponse;
import com.sim.core.port.UserGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserGateway {

    private final UaaHttpClient httpClient;


    @Override
    public Optional<GetUsersResponse> getUserDetail(UUID publicId) {
        try {
            return Optional.ofNullable(httpClient.getUserDetails(publicId));

        } catch (Exception e) {
            log.error("An error occurred getting the user details : {}", e.getMessage());
        }
        return Optional.empty();
    }
}
