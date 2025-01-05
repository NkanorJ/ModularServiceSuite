package com.sim.commons.exception;

public class RecordNotFoundException extends SimFintechException {
    public RecordNotFoundException(String message, boolean printStackTrace) {
        super(message, printStackTrace);
    }
}

