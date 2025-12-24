package com.torneo.goldesk.dto.actores.jugador;

import com.torneo.goldesk.dto.actores.PersonaBaseDTO;

public class JugadorResponseDTO extends PersonaBaseDTO {

    private Integer idJugador;
    private String urlFoto;
    private Boolean esDelegado;

    public JugadorResponseDTO(String cedula, String nombre, String apellidos, String telefono, String email, Integer idJugador, String urlFoto, Boolean esDelegado) {
        super(cedula, nombre, apellidos, telefono, email);
        this.idJugador = idJugador;
        this.urlFoto = urlFoto;
        this.esDelegado = esDelegado;
    }

    public Integer getIdJugador() {
        return idJugador;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public Boolean isEsDelegado() {
        return esDelegado;
    }
}
