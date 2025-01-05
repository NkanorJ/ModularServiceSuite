package com.sim.commons.dto.request;

import com.sim.commons.dto.response.LoginSimFintechResponse;

public record LoginSimFintechUserRequest(String email, String password) implements an.awesome.pipelinr.Command<LoginSimFintechResponse> {
}
