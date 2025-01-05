package com.sim.core.user.dto;

public record SimRegRequest(String username, String password, String email, String firstName, String lastName, String gender) {
}
