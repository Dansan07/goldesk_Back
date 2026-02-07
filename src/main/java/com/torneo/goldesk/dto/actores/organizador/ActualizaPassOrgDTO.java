package com.torneo.goldesk.dto.actores.organizador;

public class ActualizaPassOrgDTO {

    private String email;
    private String pass;

    public ActualizaPassOrgDTO(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }
}
