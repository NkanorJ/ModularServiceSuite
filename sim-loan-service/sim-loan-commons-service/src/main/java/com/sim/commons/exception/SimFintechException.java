package com.sim.commons.exception;

public abstract class SimFintechException extends RuntimeException {

    protected final ExceptionCodeEnum code;

    protected final boolean printStackTrace;

    protected SimFintechException(String message, boolean printStackTrace) {
        super(message);
        this.code = ExceptionCodeEnum.AN_ERROR_OCCURRED;
        this.printStackTrace = printStackTrace;
    }

    protected SimFintechException(ExceptionCodeEnum code, String message, boolean printStackTrace) {
        super(message);
        this.code = code;
        this.printStackTrace = printStackTrace;
    }

    public SimFintechException(String message, Exception cause, boolean printStackTrace) {
        super(message, cause);
        this.code = ExceptionCodeEnum.AN_ERROR_OCCURRED;
        this.printStackTrace = printStackTrace;
    }

    public SimFintechException(boolean printStackTrace, String message, Object... args) {
        super(String.format(message, args));
        this.code = ExceptionCodeEnum.AN_ERROR_OCCURRED;
        this.printStackTrace = printStackTrace;
    }

    public ExceptionCodeEnum getCode() {
        return code;
    }
}
