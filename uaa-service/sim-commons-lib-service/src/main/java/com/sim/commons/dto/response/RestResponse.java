package com.sim.commons.dto.response;

import com.sim.commons.exception.ExceptionCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
@NoArgsConstructor
public class RestResponse {

    private int statusCode;
    private String message;

    private String timestamp = String.valueOf(LocalDateTime.now());
    private HttpStatus status = HttpStatus.OK;
    private ExceptionCodeEnum errorCode = ExceptionCodeEnum.SUCCESSFUL;
    private String reference;

    private Map<String, Object> additionalInfo = new HashMap<>();

    public RestResponse(String message) {
        this.statusCode = status.value();
        this.message = message;
    }

    public RestResponse(String message, HttpStatus status, ExceptionCodeEnum errorCode, String reference, Map<String, Object> entry) {
        this.statusCode = status.value();
        this.message = message;
        this.status = status;
        this.errorCode = errorCode;
        this.reference = reference;
        this.additionalInfo = entry;
    }
}
