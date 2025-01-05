package com.sim.commons.exception;

public class InvalidArgumentException extends SimFintechException {

    public InvalidArgumentException(String message, boolean printStackTrace) {
        super(message, printStackTrace);
    }
}
