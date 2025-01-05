package com.sim.commons.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record GetUsersResponse(String firstName, String lastName, String gender, String email, String mobile,
                               UUID publicId, String role, LocalDate dateOfBrith, String loanStatus) {

}