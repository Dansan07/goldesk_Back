package com.torneo.goldesk.Exception;

import org.springframework.http.HttpStatus;

public class AuthException extends GoldeskException{

    public AuthException(String message) {
        super(message, "AUTH_ERROR", HttpStatus.UNAUTHORIZED);
    }
}
