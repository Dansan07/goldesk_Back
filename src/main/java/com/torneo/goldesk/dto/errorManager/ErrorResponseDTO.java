package com.torneo.goldesk.dto.errorManager;

public record ErrorResponseDTO (
        int status,
        String errorCode,
        String message,
        String path,
        long timestamp) {
}
