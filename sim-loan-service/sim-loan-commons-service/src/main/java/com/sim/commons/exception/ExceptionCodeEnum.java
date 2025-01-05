package com.sim.commons.exception;

import lombok.Getter;

@Getter
public enum ExceptionCodeEnum {
    AN_ERROR_OCCURRED("PROCESSING-ERROR-1000"),

    TWO_FACTOR_REQUIRED("TWO-FACTOR-REQUIRED-1001"),

    ID_INFORMATION_REQUIRED("ID-INFORMATION-REQUIRED-1003"),

    EXTRA_AUTHENTICATION_REQUIRED("EXTRA-AUTHENTICATION-REQUIRED-1002"),

    SUCCESSFUL("PROCESSED-SUCCESSFULLY-1004");

    private final String message;

    ExceptionCodeEnum(String message) {

        this.message = message;
    }
}
