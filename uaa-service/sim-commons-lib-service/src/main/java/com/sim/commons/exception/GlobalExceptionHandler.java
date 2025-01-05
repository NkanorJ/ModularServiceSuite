package com.sim.commons.exception;


import com.sim.commons.dto.response.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    @ExceptionHandler(SimFintechException.class)
    public final ResponseEntity<RestResponse> handleInvalidRequestException(SimFintechException ex) {
        log.error(ex.getMessage(), ex.printStackTrace ? ex : getStackTrace(ex));
        RestResponse errorDetails = new RestResponse(ex.getMessage(), BAD_REQUEST, ExceptionCodeEnum.AN_ERROR_OCCURRED, "", Map.of());
        return new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

    private static String getStackTrace(SimFintechException ex) {
        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < 5; x++) {
            builder.append(ex.getStackTrace()[x]).append("\n");
        }
        return builder.toString();
    }

    @ExceptionHandler(RateLimitException.class)
    public final ResponseEntity<RestResponse> handleRateLimitException(SimFintechException ex) {
        log.error(ex.getMessage(), ex.printStackTrace ? ex : getStackTrace(ex));
        RestResponse errorDetails = new RestResponse(ex.getMessage(), TOO_MANY_REQUESTS, ExceptionCodeEnum.AN_ERROR_OCCURRED, "", Map.of());
        return new ResponseEntity<>(errorDetails, TOO_MANY_REQUESTS);
    }

    @ExceptionHandler({RecordNotFoundException.class, NoSuchElementException.class, AccountNotFoundException.class})
    public final ResponseEntity<RestResponse> handleRecordNotFoundException(SimFintechException ex) {
        log.error(ex.getMessage(), ex.printStackTrace ? ex : getStackTrace(ex));
        RestResponse errorDetails = new RestResponse(ex.getMessage(), NOT_FOUND, ExceptionCodeEnum.AN_ERROR_OCCURRED, "", Map.of());
        return new ResponseEntity<>(errorDetails, NOT_FOUND);
    }

    @ExceptionHandler({org.springframework.security.access.AccessDeniedException.class})
    public final ResponseEntity<RestResponse> handleAccessDeniedException(org.springframework.security.access.AccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
        RestResponse errorDetails = new RestResponse(ex.getMessage(), FORBIDDEN, ExceptionCodeEnum.AN_ERROR_OCCURRED, "", Map.of());
        return new ResponseEntity<>(errorDetails, FORBIDDEN);
    }

    @ExceptionHandler({AuthenticationException.class})
    public final ResponseEntity<RestResponse> handleAuthenticationException(Exception ex) {

        if (Set.of(BadCredentialsException.class, CredentialsExpiredException.class)
                .contains(ex.getClass()))
            log.error(ex.getMessage());
        else
            log.error(ex.getMessage(), ex);

        RestResponse errorDetails = new RestResponse(ex.getMessage(), UNAUTHORIZED, ExceptionCodeEnum.AN_ERROR_OCCURRED, "", Map.of());
        return new ResponseEntity<>(errorDetails, UNAUTHORIZED);
    }


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<RestResponse> handleUncaughtException(Exception ex) {
        RestResponse errorDetails = new RestResponse("Something isn't right. We are working on getting it fixed as soon as we can.", INTERNAL_SERVER_ERROR, ExceptionCodeEnum.AN_ERROR_OCCURRED, "", Map.of());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }

}
