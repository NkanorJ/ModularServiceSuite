package com.sim.commons.exception;

public class LoanNotFoundException extends SimFintechException {

    public LoanNotFoundException(final String message, boolean printStackTrace) {
        super(message, printStackTrace);
    }
}

