package com.torneo.goldesk.dto.errorManage;

public record ErrorResponseDTO (
        int status,
        String errorCode,
        String message,
        String path,
        long timestamp) {
}
