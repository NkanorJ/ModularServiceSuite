package com.sim.commons.dto.request;

import com.sim.commons.dto.registrationdto.SecretKey;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateSimFintechRequest(@NotNull @NotEmpty String password, @NotNull @NotEmpty String email,
                                      String firstName, String lastName, String gender,
                                      @NotNull @NotEmpty String phoneNumber, String role, LocalDate dateOfBrith) implements an.awesome.pipelinr.Command<SecretKey> {
}
