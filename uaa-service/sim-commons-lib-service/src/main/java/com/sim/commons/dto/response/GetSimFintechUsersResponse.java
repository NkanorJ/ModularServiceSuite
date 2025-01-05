package com.sim.commons.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record GetSimFintechUsersResponse(String firstName, String lastName, String gender, String email, String mobile,
                                         UUID publicId, String role, LocalDate dateOfBrith, String loanStatus) {

}
