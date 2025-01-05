package com.sim.commons.dto.request;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateSimFintechRequest(String firstName, String lastName, String gender, @NotNull @NotEmpty String phoneNumber,
                                      LocalDate dateOfBirth, UUID publicId, String role) implements Command<Voidy> {
}
