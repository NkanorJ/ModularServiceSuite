package com.sim.commons.exception;

public class RateLimitException extends SimFintechException {
    public RateLimitException(String message, boolean printStackTrace) {
        super(message, printStackTrace);
    }
}
