package com.torneo.goldesk.Exception;

import org.springframework.http.HttpStatus;

public class PreconditionFailed extends GoldeskException {

    public PreconditionFailed(String message) {
        super(message, "PRECONDITION_FAILED", HttpStatus.PRECONDITION_FAILED);
    }
}
