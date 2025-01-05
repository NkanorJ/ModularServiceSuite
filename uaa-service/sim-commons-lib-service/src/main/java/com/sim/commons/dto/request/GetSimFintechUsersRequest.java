package com.sim.commons.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public record GetSimFintechUsersRequest(String firstName, String lastName, String gender, String email, String mobile,
                                        UUID publicId, String role, LocalDate dateOfBrith, String loanStatus) {

}
