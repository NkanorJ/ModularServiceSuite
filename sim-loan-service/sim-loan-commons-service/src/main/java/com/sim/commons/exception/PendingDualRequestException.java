package com.sim.commons.exception;

public class PendingDualRequestException extends SimFintechException {
    public PendingDualRequestException(String message, boolean printStackTrace) {
        super(message, printStackTrace);
    }
}

