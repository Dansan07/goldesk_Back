package com.torneo.goldesk.Exception;

import org.springframework.http.HttpStatus;

public abstract class GoldeskException extends RuntimeException{
    private final String errorCode;
    private final HttpStatus status;

    public GoldeskException(String message, String errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
