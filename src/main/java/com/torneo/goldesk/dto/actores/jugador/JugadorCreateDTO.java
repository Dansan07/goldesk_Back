package com.torneo.goldesk.dto.actores.jugador;

import com.torneo.goldesk.dto.actores.PersonaBaseDTO;

public class JugadorCreateDTO extends PersonaBaseDTO {

    private String urlFoto;
    private Boolean esDelegado;

    public JugadorCreateDTO() {
    }

    public JugadorCreateDTO(String cedula, String nombre, String apellidos, String telefono, String email, String urlFoto, Boolean esDelegado) {
        super(cedula, nombre, apellidos, telefono, email);
        this.urlFoto = urlFoto;
        this.esDelegado = esDelegado;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public Boolean isEsDelegado() {
        return esDelegado;
    }
}
