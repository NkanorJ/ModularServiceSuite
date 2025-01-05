package com.sim.commons.dto.request;

import com.sim.commons.dto.response.GetSimFintechUsersResponse;

import java.util.UUID;

public record GetSimFintechUserRequest(UUID publicId) implements an.awesome.pipelinr.Command<GetSimFintechUsersResponse> {
}
