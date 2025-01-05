package com.sim.commons.dto.request;

import com.sim.commons.dto.response.GetLoanResponse;

import java.util.UUID;

public record GetLoanRequest(UUID publicId)
        implements an.awesome.pipelinr.Command<GetLoanResponse> {

}
