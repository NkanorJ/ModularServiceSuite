package com.sim.commons.dto.request;

import an.awesome.pipelinr.Voidy;

import java.util.UUID;

public record UpdateLoanRequest(String status, UUID userId)
        implements an.awesome.pipelinr.Command<Voidy> {

}
