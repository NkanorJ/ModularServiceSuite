package com.sim.commons.dto.request;

import an.awesome.pipelinr.Voidy;

import java.util.UUID;

public record DeleteSimFintechRequest(UUID publicId) implements an.awesome.pipelinr.Command<Voidy> {
}
