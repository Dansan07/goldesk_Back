package com.torneo.goldesk.dto.partido;

public class PartidoResDuplicateDTO {

    private String status;  // "SUCCESS", "WARNING", "ERROR"
    private String message;

    public PartidoResDuplicateDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
