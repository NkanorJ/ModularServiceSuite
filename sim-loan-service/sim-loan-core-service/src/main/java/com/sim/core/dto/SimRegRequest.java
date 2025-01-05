package com.sim.core.dto;

public record SimRegRequest(String username, String password, String email, String firstName, String lastName, String gender) {
}
