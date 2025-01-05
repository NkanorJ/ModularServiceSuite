package com.sim.gateway.user;

import com.sim.commons.dto.response.GetUsersResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "sim-uaa-service", path = "/api/uaa")
interface UaaHttpClient {

    @GetMapping("/internal/getUser/{publicId}")
    GetUsersResponse getUserDetails(@PathVariable UUID publicId);

}
