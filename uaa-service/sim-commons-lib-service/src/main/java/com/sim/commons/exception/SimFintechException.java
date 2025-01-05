package com.sim.commons.exception;

public abstract class SimFintechException extends RuntimeException {

    protected final ExceptionCodeEnum code;

    protected final boolean printStackTrace;

    protected SimFintechException(String message, boolean printStackTrace) {
        super(message);
        this.code = ExceptionCodeEnum.AN_ERROR_OCCURRED;
        this.printStackTrace = printStackTrace;
    }
}
