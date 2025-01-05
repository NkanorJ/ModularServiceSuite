package com.sim.rest.controller.internal;

import an.awesome.pipelinr.Pipeline;
import com.sim.commons.dto.request.GetSimFintechUserRequest;
import com.sim.commons.dto.response.GetSimFintechUsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.sim.rest.apis.UserAPI.GET_USER;
import static com.sim.rest.apis.UserAPI.INTERNAL_BASE_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(INTERNAL_BASE_URL)
public class InternalUserController {

    private final Pipeline pipeline;

    @GetMapping(GET_USER)
    public GetSimFintechUsersResponse getUser(@PathVariable UUID publicId) {
        return pipeline.send(new GetSimFintechUserRequest(publicId));
    }

}
